package com.room.finder.mapper;

import java.util.ArrayList;

import com.room.finder.dto.AdvertisementDto;
import com.room.finder.dto.SearchAdvertisementDto;
import com.room.finder.model.Advertisement;

public interface AdvertisementMapper {
    Integer saveAdvertisement(Advertisement advertisement);
    Integer updateAdvertisement(Advertisement advertisement);
    Integer deleteAdvertisement(Integer id);
    Advertisement findPendingAdvertisementById(Integer id);
    ArrayList<AdvertisementDto> findAllAcceptedAdvertisementCreatedByLandlord(Integer landlordId);
    ArrayList<Advertisement> findAllUnderReviewAdvertisement();
    Advertisement findPendingAdvertisementByIdAndUser(SearchAdvertisementDto advertisementDto);
    Advertisement findUnderReviewAdvertisementById(Integer advertisementId);
   Advertisement findAdvertisementById(Integer advertisementId);
}
