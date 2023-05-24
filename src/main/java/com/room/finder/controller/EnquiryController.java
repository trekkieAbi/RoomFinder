package com.room.finder.controller;

import com.room.finder.model.Enquiry;
import com.room.finder.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.security.Principal;

@RestController
@RequestMapping("/enquiry")
public class EnquiryController {
    @Autowired
    private EnquiryService enquiryService;

    @RequestMapping(value = "/make",method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('make_enquiry')")
    ResponseEntity<String> makeEnquiryController(@RequestBody Enquiry enquiry, Principal principal) throws MessagingException {
        Integer status=enquiryService.makeEnquiry(enquiry,principal);
        if(status==0){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Enquiry for a particular room has been make and email to the landlord has been sent successfully!!");
    }
}
