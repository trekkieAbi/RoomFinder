package com.room.finder.mapper;
import com.room.finder.model.Advertisement;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;

@Mapper
public interface AdvertisementMapper {
    Integer saveAdvertisement(Advertisement advertisement);
    Integer updateAdvertisement(Advertisement advertisement);
    Integer deleteAdvertisement(Integer id);
    Advertisement findPendingAdvertisementById(Integer id);
    ArrayList<Advertisement> findAllAcceptedAdvertisement();
    ArrayList<Advertisement> findAllUnderReviewAdvertisement();
}
