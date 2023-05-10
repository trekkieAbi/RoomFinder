package com.room.finder.mapper;
import com.room.finder.model.Landlord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LandlordMapper {
Integer saveLandlord(Landlord landlord);
Integer updateLandlord(Landlord landlord);
Landlord findLandlordById(Integer id);
Landlord findLandlordByUser(Integer id);
}
