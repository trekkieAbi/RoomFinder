package com.room.finder.service.impl;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.room.finder.constant.AppConstant;
import com.room.finder.dto.AdvertisementDto;
import com.room.finder.dto.RoomDto;
import com.room.finder.mapper.AdvertisementMapper;
import com.room.finder.mapper.CustomerMapper;
import com.room.finder.mapper.EnquiryMapper;
import com.room.finder.mapper.UserMapper;
import com.room.finder.model.Advertisement;
import com.room.finder.model.Customer;
import com.room.finder.model.Email;
import com.room.finder.model.Enquiry;
import com.room.finder.model.User;
import com.room.finder.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {
    @Autowired
    private EnquiryMapper enquiryMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Autowired
    private EmailServiceImpl emailService;
    ExecutorService executorService= Executors.newSingleThreadExecutor();
    @Override
    @Transactional(rollbackFor = {RuntimeException.class,MessagingException.class})
    public Integer makeEnquiry(Enquiry enquiry, Principal principal) throws MessagingException {
        Integer status=0;
            Customer retrievedCustomer=getCustomer(principal);
            Advertisement retrievedAdvertisement=getAdvertisement(enquiry.getRoomId());
            if(retrievedAdvertisement==null){
                return status;
            }
            enquiry.setCustomerId(retrievedCustomer.getId());
                enquiry.setRoomId(enquiry.getRoomId());
                try{
                     status=enquiryMapper.makeEnquiry(enquiry);
                    if(status>0) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    emailService.sendHtmlMessage(getWrittenEmail(enquiry.getRoomId(), retrievedAdvertisement.getId(), principal));
                                } catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });
                        executorService.shutdown();
                    }
                    else {
                        throw new RuntimeException("something went wrong");
                    }
                }catch (RuntimeException e) {
                    throw new MessagingException(e.getLocalizedMessage());
                }
                return status;
    }

    private Customer getCustomer(Principal principal){
        User retrievedUser=userMapper.findUserByEmail(principal.getName());
        Customer retrievedCustomer=customerMapper.findCustomerByUser(retrievedUser.getId());
        return retrievedCustomer;
    }

    private Boolean checkWhetherRoomIsVisible(Integer roomId){
     Advertisement advertisement=advertisementMapper.selectAcceptedAdvertisementByRoomId(roomId);
     if(advertisement==null){
         return false;
     }
     return true;

    }
    Advertisement getAdvertisement(Integer roomId){
        Advertisement advertisement=advertisementMapper.selectAcceptedAdvertisementByRoomId(roomId);
        if(checkWhetherRoomIsVisible(roomId)){
            return advertisement;
        }
        return null;
    }

    private String getEmail(Integer advertisementId){
        return advertisementMapper.selectLandlordEmailByAdvertisementId(advertisementId);
    }


    private String getBodyForEnquiryMail(Integer roomId){
        AdvertisementDto advertisementDto=advertisementMapper.selectAdvertisementForEnquiryMailBody(roomId);
        String roomName="";

        for (RoomDto eachRoomDto:advertisementDto.getRoomDtoList()
             ) {
            roomName=eachRoomDto.getName();
        }
       String advertisementTitle=advertisementDto.getTitle();
        return String.format("Your %s room of the %s advertisement has made an enquiry by the customer",roomName,advertisementTitle);
    }

    private Email getWrittenEmail(Integer roomId,Integer advertisementId,Principal principal){
        Map<String,Object> properties=new HashMap<>();
        Email email=new Email();
        email.setTo(getEmail(advertisementId));
        email.setFrom(AppConstant.FROM);
        email.setSubject(AppConstant.SUBJECT_ENQUIRYMAIL);
        email.setText(getBodyForEnquiryMail(roomId));
        properties.put("name",getLandlordName(principal));
        properties.put("subject",email.getSubject());
        properties.put("body",email.getText());
        email.setProperties(properties);
        return email;
    }
    String getLandlordName(Principal principal){
       return userMapper.findUserByEmail(principal.getName()).getName();
    }



}
