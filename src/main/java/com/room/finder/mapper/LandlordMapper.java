package com.room.finder.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.room.finder.model.Landlord;

@Mapper
public interface LandlordMapper {
Integer saveLandlord(Landlord landlord);
Integer updateLandlord(Landlord landlord);
Landlord findLandlordById(Integer id);
Landlord findLandlordByUser(Integer userId);
}
