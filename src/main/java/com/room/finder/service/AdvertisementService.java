package com.room.finder.service;

import com.room.finder.dto.AdvertisementDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AdvertisementService {
    Map<Integer,String> createAdvertisement(AdvertisementDto advertisementDto, MultipartFile[] multipartFiles);
}
