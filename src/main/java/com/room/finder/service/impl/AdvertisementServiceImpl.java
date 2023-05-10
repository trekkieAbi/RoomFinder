package com.room.finder.service.impl;

import com.room.finder.dto.AdvertisementDto;
import com.room.finder.service.AdvertisementService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Override
    public Map<Integer, String> createAdvertisement(AdvertisementDto advertisementDto, MultipartFile[] multipartFiles) {
        return null;
    }
}
