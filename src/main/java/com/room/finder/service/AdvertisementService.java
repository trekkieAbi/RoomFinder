package com.room.finder.service;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.room.finder.dto.AdvertisementDto;

public interface AdvertisementService {
	   Map<Integer,String> saveAdvertisement(AdvertisementDto advertisementDto, MultipartFile[] multipartFiles, Principal principal) throws IOException;
	    Integer submitAdvertisement(Integer advertisementId,Principal principal);

	    Map<Integer,String> acceptAdvertisement(Integer advertisementId) throws MessagingException;
	    Map<Integer,String> rejectAdvertisement(Integer advertisementId,Principal principal) throws MessagingException;
	    ArrayList<AdvertisementDto> getAllAcceptedAdvertisement(Principal principal);
	    Map<Integer,String> editAdvertisement(Integer advertisementId,Principal principal);
}
