package com.room.finder.service;

import com.room.finder.model.Favourite;

import java.util.Map;

public interface FavouriteService {
    Map<Integer,String> AddToFavourite(Favourite favourite);
}
