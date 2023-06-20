package com.room.finder.mapper;

import com.room.finder.dto.FavouriteDto;
import com.room.finder.dto.RemoveRoomFromFavDto;
import com.room.finder.model.Favourite;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface FavouriteMapper {
    Integer addFavourite(Favourite favourite);
   Integer selectFavouriteByRoomAndUserWithAdvertisement(RemoveRoomFromFavDto removeRoomFromFavDto);
    Integer deleteRoomFromFav(Integer favId);

    FavouriteDto findAllFavouriteRoom(Integer customerId);
}
