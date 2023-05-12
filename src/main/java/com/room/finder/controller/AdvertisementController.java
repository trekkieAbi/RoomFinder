package com.room.finder.controller;

import com.room.finder.dto.AdvertisementDto;
import com.room.finder.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    ResponseEntity<Map<Integer,String>> saveAdvertisementController(@RequestBody AdvertisementDto advertisementDto, @RequestParam("multipartFiles")MultipartFile[] multipartFiles, Principal principal) throws IOException {
        Map<Integer,String> message=advertisementService.saveAdvertisement(advertisementDto,multipartFiles,principal);
        if(message.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @RequestMapping(value = "/submit",method = RequestMethod.PUT)
    ResponseEntity<String> submitAdvertisementController(@PathVariable Integer advertisementId,Principal principal){
        Integer advertisement=advertisementService.submitAdvertisement(advertisementId,principal);
        String message=advertisement>0?"advertisement submitted successfully":"Provided advertisement is invalid for submit";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
