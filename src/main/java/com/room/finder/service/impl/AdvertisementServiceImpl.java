package com.room.finder.service.impl;

import com.room.finder.constant.AppConstant;
import com.room.finder.dto.AdvertisementDto;
import com.room.finder.dto.RentRangeSearchDto;
import com.room.finder.dto.RoomDto;
import com.room.finder.dto.SearchAdvertisementDto;
import com.room.finder.mapper.AdvertisementMapper;
import com.room.finder.mapper.LandlordMapper;
import com.room.finder.mapper.RoomMapper;
import com.room.finder.mapper.UserMapper;
import com.room.finder.model.*;
import com.room.finder.service.AdvertisementService;
import com.room.finder.service.ImageDeleteService;
import com.room.finder.util.AdvertisementHelper;
import com.room.finder.validation.AdvertisementValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    ImageDeleteService imageDeleteService;
    @Autowired
    ImageUploadService imageUploadService;
    @Autowired
    AdvertisementHelper advertisementHelper;

    ExecutorService executorService= Executors.newSingleThreadExecutor();

    @Override
    public Map<Integer,String> saveAdvertisement(AdvertisementDto advertisementDto, MultipartFile[] multipartFiles, Principal principal) throws IOException {
        User loggedInUser=userMapper.findUserByEmail(principal.getName());
        Landlord landlord=landlordMapper.findLandlordByUser(loggedInUser.getId());
        Map<Integer,String> message=new HashMap<>();
        Advertisement advertisement=new Advertisement();
        if(AdvertisementValidation.validateRoomAvailableDate(advertisementDto.getRoomAvailableDate())){
            message.put(500,"Invalid room available date");
            return message;
        }
        if(AdvertisementValidation.validateRoomImage(multipartFiles)){
            advertisement.setTitle(advertisementDto.getTitle());
            advertisement.setAddress(advertisementDto.getAddress());
            advertisement.setRent(advertisementDto.getRent());
            advertisement.setDescription(advertisementDto.getDescription());
            advertisement.setStatus(AdvertisementStatus.pending);
            advertisement.setRoomAvailableDate(advertisementDto.getRoomAvailableDate());
            advertisement.setNumberOfRoom(multipartFiles.length);
            advertisement.setLandlordId(landlord.getId());
            advertisementMapper.saveAdvertisement(advertisement);
            advertisementDto.setId(advertisement.getId());
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
        advertisement.setStatus(AdvertisementStatus.under_review);

        if(AdvertisementValidation.validateModeratorSelectionStatus(advertisement.getStatus().toString())){//checking whether the landlord selecting the wrong status
            status=advertisementMapper.updateAdvertisement(advertisement);

        }
        else{
            throw new RuntimeException("Invalid advertisement status selection by the landlord");
        }
        return status;
    }

    @Override
    @Transactional(rollbackFor = MessagingException.class)
    public Map<Integer,String> acceptAdvertisement(Integer advertisementId) throws MessagingException {
        Map<Integer,String> message=new HashMap<>();
        Advertisement submittedAdvertisement=advertisementMapper.findUnderReviewAdvertisementById(advertisementId);
        if(submittedAdvertisement==null){
            message.put(500,"something went wrong");
            return message;
        }
        submittedAdvertisement.setStatus(AdvertisementStatus.accepted);
        Integer status=advertisementMapper.updateAdvertisement(submittedAdvertisement);
        if(status==1){

            Landlord landlord=landlordMapper.findLandlordById(submittedAdvertisement.getLandlordId());
            User retrievedUser=userMapper.findUserById(landlord.getUserId());
           Email retrievedEmail=getEmail(retrievedUser,submittedAdvertisement);
           try {
               executorService.execute(new Runnable() {
                   @Override
                   public void run() {
                       emailService.sendSimpleMail(retrievedEmail);
                   }
               });
               executorService.shutdown();
           }catch (RuntimeException e){
               throw new MessagingException(e.getLocalizedMessage());
           }

           message.put(200,"email has been sent to the landlord's registered gmail,please check it for more information");
        }

        return message;
    }

    @Transactional(rollbackFor = MessagingException.class)
    @Override
    public Map<Integer,String> rejectAdvertisement(Integer advertisementId) throws MessagingException {
        Email email;
        Map<Integer,String> message=new HashMap<>();
        Advertisement submittedAdvertisement=advertisementMapper.findUnderReviewAdvertisementById(advertisementId);
        if(submittedAdvertisement==null){
            message.put(500,"something went wrong");
            return message;
        }
        submittedAdvertisement.setStatus(AdvertisementStatus.rejected);
        Integer status=advertisementMapper.updateAdvertisement(submittedAdvertisement);
        if(status==1){
            Landlord landlord=landlordMapper.findLandlordById(submittedAdvertisement.getLandlordId());
            User retrievedUser=userMapper.findUserById(landlord.getUserId());
            email=getEmail(retrievedUser,submittedAdvertisement);

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            emailService.sendHtmlMessage(email);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                executorService.shutdown();
                message.put(200,"email has been sent to the landlord's registered gmail,please check it for more information");
        }
        return message;
    }

    @Override
    public ArrayList<AdvertisementDto> getAllAcceptedAdvertisementForLandlord(Principal principal) {
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
    public ArrayList<AdvertisementDto> getAllAcceptedAdvertisement() {
        ArrayList<AdvertisementDto> advertisementDtoList= advertisementMapper.findAllAcceptedAdvertisement();
        return advertisementDtoList;
    }
    @Transactional
    @Override
    public Integer deleteAdvertisement(Integer advertisementId, Principal principal) throws IOException {
        Integer resultStatus=0;
        SearchAdvertisementDto advertisementDto=new SearchAdvertisementDto();
        Landlord landlord=getLandlord(principal);
        advertisementDto.setLandlordId(landlord.getId());
        advertisementDto.setAdvertisementId(advertisementId);
       Advertisement advertisement=advertisementMapper.findAdvertisementByLandlordIdAndAdvertisementId(advertisementDto);
       if(advertisement.getStatus().equals(AdvertisementStatus.accepted)||advertisement.getStatus().equals(AdvertisementStatus.rejected)){
           ArrayList<RoomDto> roomDtos=roomMapper.selectRoomDtoByAdvertisement(advertisementId);
           if(!roomDtos.isEmpty()){
               deleteRoomWithImage(roomDtos);
           }
          resultStatus=advertisementMapper.deleteAdvertisement(advertisementId);
       }
        return resultStatus;
    }

    @Override
    public ArrayList<AdvertisementDto> searchAdvertisementByAddress(String address) {
       return advertisementMapper.searchRoomByAddress(address);
    }

    @Override
    public ArrayList<AdvertisementDto> searchAdvertisementByRentRange(RentRangeSearchDto rentRangeSearchDto) {
        return advertisementMapper.searchRoomByRent(rentRangeSearchDto);
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor = {RuntimeException.class,IOException.class})
    @Override
    public Map<Integer, String> editAdvertisement(AdvertisementDto advertisementDto, Principal principal,MultipartFile[] multipartFiles) throws IOException {
        Map<Integer,String> message=new HashMap<>();
        Landlord landlord=getLandlord(principal);
        Advertisement retrievedAdvertisement=advertisementMapper.findAdvertisementById(advertisementDto.getId());
        if(retrievedAdvertisement==null&&!retrievedAdvertisement.getLandlordId().equals(landlord.getId())){
            message.put(500,"No advertisement with the given advertisement id found and loggedIn user role invalid for the given ad. edit operation!!!!!");
            return message;
        }
        if(retrievedAdvertisement.getStatus().equals(AdvertisementStatus.accepted)||retrievedAdvertisement.getStatus().equals(AdvertisementStatus.rejected)){
            String oldStatus=retrievedAdvertisement.getStatus().toString();
            retrievedAdvertisement.setStatus(AdvertisementStatus.pending);
            advertisementMapper.updateAdvertisement(retrievedAdvertisement);
            if(!advertisementDto.getRoomId().isEmpty() &&advertisementDto.getRoomId().size()==multipartFiles.length){
                if(AdvertisementValidation.validateRoomImage(multipartFiles)) {
                    if (checkUpdatingImageTypeBeforeDelete(multipartFiles)) {
                        List<RoomDto> roomDtos = getAllRoomDtoFromIds(advertisementDto.getRoomId(), advertisementDto.getId());
                        if (roomDtos.isEmpty()) {
                            message.put(500, "Something went wrong!!!");
                            changeAdvertisementStatusToAcceptedOrRejected(oldStatus, retrievedAdvertisement);
                            return message;
                        }
                        if (deleteRoomWithImage(roomDtos)) {
                            advertisementHelper.createAndSaveRoom(multipartFiles, advertisementDto);
                        }
                    }
                    } else {
                        message.put(500, "Invalid number of room image provided by the landlord");
                        changeAdvertisementStatusToAcceptedOrRejected(oldStatus, retrievedAdvertisement);
                        return message;
                    }


            }else {
                message.put(500,"No of image doesnot match with the list of room id to be updated");
                changeAdvertisementStatusToAcceptedOrRejected(oldStatus,retrievedAdvertisement);
                return message;
            }
            if(AdvertisementValidation.validateRoomAvailableDate(advertisementDto.getRoomAvailableDate())){
                retrievedAdvertisement.setRoomAvailableDate(advertisementDto.getRoomAvailableDate());
            }
            if(advertisementDto.getTitle()!=null){
                retrievedAdvertisement.setTitle(advertisementDto.getTitle());
            }
            if(advertisementDto.getDescription()!=null){
                retrievedAdvertisement.setDescription(advertisementDto.getDescription());
            }
            if(advertisementDto.getAddress()!=null){
                retrievedAdvertisement.setAddress(advertisementDto.getAddress());
            }
            if(advertisementDto.getRent()!=null){
                if(AdvertisementValidation.validateRoomRent(advertisementDto.getRent())){
                    retrievedAdvertisement.setRent(advertisementDto.getRent());
                }else {
                    message.put(500,"Invalid room rent provided by the landlord!!!");
                    changeAdvertisementStatusToAcceptedOrRejected(oldStatus,retrievedAdvertisement);
                    return message;
                }
            }
            Integer status=advertisementMapper.updateAdvertisement(retrievedAdvertisement);
            if(status<=0){
                throw new RuntimeException("Something went wrong with the db operation!!!");
            }
            message.put(200,"advertisement editted successfully by the landlord");
        }
        else {
            message.put(500,"invalid advertisement status to perform edit operaation!!!");
        }
        return message;
    }



    private Email getEmail(User retrievedUser,Advertisement advertisement){
        Map<String,Object> properties=new HashMap<>();

        Email email=new Email();
        email.setFrom(AppConstant.FROM);
        email.setTo(retrievedUser.getEmail());
        switch (advertisement.getStatus().toString()){
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
        Landlord landlord=landlordMapper.findLandlordByUser(loggedInUser.getId());
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

    private Boolean checkWhetherRoomExistsInGivenAdvertisement(List<Integer> roomId,Integer advertisementId){
        Boolean status=false;
       ArrayList<RoomDto> roomDtos=roomMapper.selectRoomDtoByAdvertisement(advertisementId);
        ArrayList<RoomDto> roomDtos1=new ArrayList<>();
        RoomDto roomDto;
        for (Integer eachRoom:roomId
             ) {
            roomDto=roomMapper.selecRoomById(eachRoom);
            roomDtos1.add(roomDto);

        }
        if(roomDtos.contains(roomDtos1)){
            status=true;

        }
        return true;
    }

    private List<RoomDto> getAllRoomDtoFromIds(List<Integer> id,Integer advertisementId){
        List<RoomDto> roomDtos=new ArrayList<>();
        RoomDto roomDto;
        if(checkWhetherRoomExistsInGivenAdvertisement(id,advertisementId)){
            for (Integer eachId:id
                 ) {
                roomDto=roomMapper.selecRoomById(eachId);
                roomDtos.add(roomDto);
            }
        }else{
            return null;
        }
        return roomDtos;
    }

    @Transactional(rollbackFor ={IOException.class,RuntimeException.class,SQLException.class})
    public boolean deleteRoomWithImage(List<RoomDto> roomDtos) throws IOException {
        boolean status=false;
        for (RoomDto eachRoomDto:roomDtos
             ) {
                if(imageDeleteService.deleteImage(eachRoomDto.getImage())){
                    Integer result=roomMapper.deleteRoom(eachRoomDto.getId());
                    if(result>0) {
                        status = true;
                    }
                    else {
                        throw new RuntimeException("error occurred while deleting the room");
                    }
                }else {
                    throw new IOException("error occurred while deleting the image");
                }
        }
        return status;
    }

    private void changeAdvertisementStatusToAcceptedOrRejected(String oldStatus,Advertisement advertisement){
        switch (oldStatus){
            case "Accepted":
                advertisement.setStatus(AdvertisementStatus.accepted);
                advertisementMapper.updateAdvertisement(advertisement);
                break;
            case "Rejected":
                advertisement.setStatus(AdvertisementStatus.rejected);
                advertisementMapper.updateAdvertisement(advertisement);
                break;

        }

    }

    private boolean checkUpdatingImageTypeBeforeDelete(MultipartFile[] multipartFiles){//it is done for data integrity so that we check exception occured before deleting previous image
        boolean status=false;
        for (MultipartFile eachFile:multipartFiles
             ) {
            if(AdvertisementValidation.validateImageFile(eachFile.getOriginalFilename())){
               status=true;
            }
            else {
                throw new  RuntimeException("Invalid file type");
            }
        }
        return status;

    }





}
