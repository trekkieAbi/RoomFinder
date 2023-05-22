package com.room.finder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room.finder.dto.AdvertisementDto;
import com.room.finder.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('save_advertisement')")
    ResponseEntity<Map<Integer,String>> saveAdvertisementController(@RequestParam("advertisement") String advertisementDto, @RequestParam("multipartFiles")MultipartFile[] multipartFiles, Principal principal) throws IOException {
        AdvertisementDto advertisementDto1 =getAdvertisementDto(advertisementDto);
        Map<Integer,String> message=advertisementService.saveAdvertisement(advertisementDto1,multipartFiles,principal);
        if(message.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @RequestMapping(value = "/submit/{advertisementId}",method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('submit_advertisement')")
    ResponseEntity<String> submitAdvertisementController(@PathVariable Integer advertisementId,Principal principal){
        Integer advertisement=advertisementService.submitAdvertisement(advertisementId,principal);
        String message=advertisement>0?"advertisement submitted successfully":"Provided advertisement is invalid for submit";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @RequestMapping(value = "/accept/{advertisementId}",method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('approve_advertisement')")
    ResponseEntity<Map<Integer,String>> acceptAdvertisementController(@PathVariable Integer advertisementId) throws MessagingException {
        Map<Integer,String>  message =advertisementService.acceptAdvertisement(advertisementId);
        if(message.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @RequestMapping(value = "/reject/{advertisementId}",method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('reject_advertisement')")
    ResponseEntity<Map<Integer,String>> rejectAdvertisementController(@PathVariable Integer advertisementId) throws MessagingException {
        Map<Integer,String>  message =advertisementService.rejectAdvertisement(advertisementId);
        if(message.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @GetMapping("/readByLandlord")
    @PreAuthorize("hasAuthority('view_accepted_advertisement')")
    ResponseEntity<List<AdvertisementDto>> viewAcceptedAdvertisementControllerForLandlord(Principal principal){
        List<AdvertisementDto> advertisementDtos=advertisementService.getAllAcceptedAdvertisementForLandlord(principal);
        return  ResponseEntity.status(HttpStatus.OK).body(advertisementDtos);
    }
    @GetMapping("/readByCusAndMod")
    @PreAuthorize("hasAuthority('view_accepted_advertisement_for_mod_cus')")
    ResponseEntity<List<AdvertisementDto>> viewAcceptedAdvertisement(){
        ArrayList<AdvertisementDto> advertisementDtos=advertisementService.getAllAcceptedAdvertisement();
        return ResponseEntity.status(HttpStatus.OK).body(advertisementDtos);
    }
    @RequestMapping(value = "/edit")
    @PreAuthorize("hasAuthority('update_advertisement')")
    ResponseEntity<Map<Integer,String>> editAdvertisementController(@RequestParam("advertisement") String advertisementDto, @RequestParam("multipartFiles")MultipartFile[] multipartFiles,Principal principal) throws IOException {
        AdvertisementDto advertisementDtoObj = getAdvertisementDto(advertisementDto);
        Map<Integer,String> message=advertisementService.editAdvertisement(advertisementDtoObj,principal,multipartFiles);
        if(message.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    private AdvertisementDto getAdvertisementDto(String advertisementDto) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(advertisementDto,AdvertisementDto.class);
    }



}
