package com.room.finder.service.impl;

import com.room.finder.constant.AppConstant;
import com.room.finder.dto.AdvertisementDto;
import com.room.finder.dto.RoomDto;
import com.room.finder.dto.SearchAdvertisementDto;
import com.room.finder.mapper.AdvertisementMapper;
import com.room.finder.mapper.LandlordMapper;
import com.room.finder.mapper.RoomMapper;
import com.room.finder.mapper.UserMapper;
import com.room.finder.model.*;
import com.room.finder.service.AdvertisementService;
import com.room.finder.util.AdvertisementHelper;
import com.room.finder.validation.AdvertisementValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Transactional
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;
    @Autowired
    private LandlordMapper landlordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoomMapper roomMapper;
    EmailServiceImpl emailService=new EmailServiceImpl();

    @Override
    public Map<Integer, String> saveAdvertisement(AdvertisementDto advertisementDto, MultipartFile[] multipartFiles, Principal principal) throws IOException {
        User loggedInUser=userMapper.findUserByEmail(principal.getName());
        Landlord landlord=landlordMapper.findLandlordByUser(loggedInUser.getId());
        Map<Integer,String> message=new HashMap<>();
        Advertisement advertisement=new Advertisement();
        if(AdvertisementValidation.validateRoomAvailableDate(advertisementDto.getRoomAvailableDate())){
            message.put(500,"Invalid room available date");
            return message;
        }
        if(AdvertisementValidation.validateRoomImage(multipartFiles)){
            advertisement.setAddress(advertisement.getAddress());
            advertisement.setRent(advertisementDto.getRent());
            advertisement.setDescription(advertisementDto.getDescription());
            advertisement.setStatus(AdvertisementStatus.pending.toString());
            advertisement.setRoomAvailableDate(advertisementDto.getRoomAvailableDate());
            advertisement.setNumberOfRoom(multipartFiles.length);
            advertisement.setLandlordId(landlord.getId());
            advertisementMapper.saveAdvertisement(advertisement);
            advertisementDto.setId(advertisement.getId());
            AdvertisementHelper advertisementHelper=new AdvertisementHelper();
            advertisementHelper.createAndSaveRoom(multipartFiles,advertisementDto);
            message.put(200,"advertisement created successfully");
        }else {
            message.put(500,"Number of images exceed the given limit!!!");
        }
        return message;
    }

    @Override
    public Integer submitAdvertisement(Integer advertisementId, Principal principal) {
        Integer status=0;
        SearchAdvertisementDto advertisementDto=new SearchAdvertisementDto();
        advertisementDto.setAdvertisementId(advertisementId);
        User loggedInUser=userMapper.findUserByEmail(principal.getName());
        advertisementDto.setLandlordId(landlordMapper.findLandlordByUser(loggedInUser.getId()).getId());
        Advertisement advertisement=advertisementMapper.findPendingAdvertisementByIdAndUser(advertisementDto);
        if(advertisement==null){
            return status;
        }
        advertisement.setStatus(AdvertisementStatus.under_review.toString());

        if(AdvertisementValidation.validateModeratorSelectionStatus(advertisement.getStatus())){//checking whether the landlord selecting the wrong status
            status=advertisementMapper.updateAdvertisement(advertisement);

        }
        else{
            throw new RuntimeException("Invalid advertisement status selection by the landlord");
        }
        return status;
    }

    @Override
    public Map<Integer,String> acceptAdvertisement(Integer advertisementId) throws MessagingException {
        Map<Integer,String> message=new HashMap<>();
        Advertisement submittedAdvertisement=advertisementMapper.findUnderReviewAdvertisementById(advertisementId);
        if(submittedAdvertisement==null){
            message.put(500,"something went wrong");
            return message;
        }
        submittedAdvertisement.setStatus(AdvertisementStatus.accepted.toString());
        Integer status=advertisementMapper.updateAdvertisement(submittedAdvertisement);
        if(status==1){
            Landlord landlord=landlordMapper.findLandlordById(submittedAdvertisement.getLandlordId());
            User retrievedUser=userMapper.findUserById(landlord.getUserId());
           Email retrievedEmail=getEmail(retrievedUser,submittedAdvertisement);
           emailService.sendHtmlMessage(retrievedEmail);
           message.put(200,"email has been sent to the landlord's registered gmail,please check it for more information");
        }

        return message;
    }

    @Override
    public Map<Integer,String> rejectAdvertisement(Integer advertisementId, Principal principal) throws MessagingException {
        Email email;
        Map<Integer,String> message=new HashMap<>();
        Advertisement submittedAdvertisement=advertisementMapper.findUnderReviewAdvertisementById(advertisementId);
        if(submittedAdvertisement==null){
            message.put(500,"something went wrong");
            return message;
        }
        submittedAdvertisement.setStatus(AdvertisementStatus.rejected.toString());
        Integer status=advertisementMapper.updateAdvertisement(submittedAdvertisement);
        if(status==1){
            Landlord landlord=landlordMapper.findLandlordById(submittedAdvertisement.getLandlordId());
            User retrievedUser=userMapper.findUserById(landlord.getUserId());
            email=getEmail(retrievedUser,submittedAdvertisement);
            emailService.sendHtmlMessage(email);
            message.put(200,"email has been sent to the landlord's registered gmail,please check it for more information");
        }
        return message;
    }

    @Override
    public ArrayList<AdvertisementDto> getAllAcceptedAdvertisement(Principal principal) {
        Landlord landlord=getLandlord(principal);
        ArrayList<AdvertisementDto> advertisementDtos=advertisementMapper.findAllAcceptedAdvertisementCreatedByLandlord(landlord.getId());
        if(advertisementDtos.isEmpty()){
           return null;
        }
        for (AdvertisementDto eachAdvertisementDto:advertisementDtos
             ) {
            ArrayList< RoomDto> roomDtos=roomMapper.selectRoomDtoByAdvertisement(eachAdvertisementDto.getId());
            eachAdvertisementDto.setRoomDtoList(roomDtos);
        }
        return advertisementDtos;
    }

    @Override
    public Map<Integer, String> editAdvertisement(Integer advertisementId, Principal principal) {
        Map<Integer,String> message=new HashMap<>();
        Landlord landlord=getLandlord(principal);
        Advertisement retrievedAdvertisement=advertisementMapper.findAdvertisementById(advertisementId);
        if(retrievedAdvertisement==null){
            message.put(500,"No advertisement with the given advertisement id found");
            return message;
        }
        ArrayList<AdvertisementDto> advertisementDtos=getAllAcceptedAdvertisement(principal);
        AdvertisementDto advertisementDto=getAdvertisementDto(retrievedAdvertisement);
        if(advertisementDtos.contains(advertisementDto)){

        }else {
            message.put(500,"something went wrong!!!");
            return message;
        }

        return null;
    }

    private Email getEmail(User retrievedUser,Advertisement advertisement){
        Map<String,Object> properties=new HashMap<>();

        Email email=new Email();
        email.setFrom(AppConstant.FROM);
        email.setTo(retrievedUser.getEmail());
        switch (advertisement.getStatus()){
            case "accepted":
                email.setSubject(AppConstant.ACCEPTED_SUBJECT);
                email.setText(AppConstant.ACCEPTED_BODY);
                break;
            case "rejected":
                email.setSubject(AppConstant.REJECTED_SUBJECT);
                email.setText(AppConstant.REJECTED_BODY);
                break;
        }
        properties.put("name",retrievedUser.getName());
        properties.put("subject",email.getSubject());
        properties.put("body",email.getText());
        email.setProperties(properties);
        return email;
    }

    private Landlord getLandlord(Principal principal){
        User loggedInUser=userMapper.findUserByEmail(principal.getName());
        Landlord landlord=landlordMapper.findLandlordById(loggedInUser.getId());
        return landlord;

    }
    private AdvertisementDto getAdvertisementDto(Advertisement advertisement){
        AdvertisementDto advertisementDto=new AdvertisementDto();
        advertisementDto.setId(advertisement.getId());
        advertisementDto.setTitle(advertisement.getTitle());
        advertisementDto.setRent(advertisement.getRent());
        advertisementDto.setDescription(advertisement.getDescription());
        advertisementDto.setAddress(advertisement.getAddress());
        advertisementDto.setRoomAvailableDate(advertisement.getRoomAvailableDate());
        return advertisementDto;
    }





}
