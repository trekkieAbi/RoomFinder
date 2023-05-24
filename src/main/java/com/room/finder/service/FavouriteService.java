package com.room.finder.service;

import com.room.finder.dto.FavouriteDto;
import com.room.finder.dto.RemoveRoomFromFavDto;
import com.room.finder.dto.RoomDto;
import com.room.finder.model.Favourite;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

public interface FavouriteService {
    Map<Integer,String> AddToFavourite(Favourite favourite, Principal principal);
    Map<Integer,String> RemoveRoomFromFavourite(RemoveRoomFromFavDto removeRoomFromFavDto,Principal principal);
    FavouriteDto getAllFavouriteRoom(Principal principal);
}
