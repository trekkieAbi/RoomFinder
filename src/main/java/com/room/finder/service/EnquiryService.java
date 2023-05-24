package com.room.finder.service;

import com.room.finder.model.Enquiry;

import javax.mail.MessagingException;
import java.security.Principal;

public interface EnquiryService {
   Integer makeEnquiry(Enquiry enquiry, Principal principal) throws MessagingException;

}
