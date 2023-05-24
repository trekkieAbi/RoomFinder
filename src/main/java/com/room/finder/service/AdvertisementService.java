package com.room.finder.service;
import com.room.finder.dto.AdvertisementDto;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

public interface AdvertisementService {
    Map<Integer,String> saveAdvertisement(AdvertisementDto advertisementDto, MultipartFile[] multipartFiles, Principal principal) throws IOException;
    Integer submitAdvertisement(Integer advertisementId,Principal principal);

    Map<Integer,String> acceptAdvertisement(Integer advertisementId) throws MessagingException;
    Map<Integer,String> rejectAdvertisement(Integer advertisementId) throws MessagingException;
    ArrayList<AdvertisementDto> getAllAcceptedAdvertisementForLandlord(Principal principal);
    Map<Integer,String> editAdvertisement(AdvertisementDto advertisementDto,Principal principal,MultipartFile[] multipartFiles) throws IOException;

    ArrayList<AdvertisementDto> getAllAcceptedAdvertisement();

    Integer deleteAdvertisement(Integer advertisementId,Principal principal) throws IOException;

    ArrayList<AdvertisementDto> searchAdvertisementByAddress(String address);

}
