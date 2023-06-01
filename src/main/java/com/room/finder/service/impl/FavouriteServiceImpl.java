package com.room.finder.service.impl;

import com.room.finder.dto.FavouriteDto;
import com.room.finder.dto.RemoveRoomFromFavDto;
import com.room.finder.dto.RoomDto;
import com.room.finder.mapper.*;
import com.room.finder.model.Advertisement;
import com.room.finder.model.Customer;
import com.room.finder.model.Favourite;
import com.room.finder.model.User;
import com.room.finder.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class FavouriteServiceImpl implements FavouriteService {
    @Autowired
    private CustomerMapper  customerMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdvertisementMapper advertisementMapper;
    @Autowired
    private FavouriteMapper favouriteMapper;
    @Override
    public Map<Integer, String> AddToFavourite(Favourite favourite,Principal  principal) {
        Map<Integer,String> message=new HashMap<>();
       Advertisement acceptedAdvertisement =advertisementMapper.findAcceptedAdvertisementById(favourite.getAdvertisementId());
       if(acceptedAdvertisement==null){
           message.put(500,"invalid advertisement status");
           return message;
       }
       ArrayList<RoomDto> roomDtoArrayList=roomMapper.selectRoomDtoByAdvertisement(acceptedAdvertisement.getId());
       if(roomDtoArrayList.isEmpty()){
           message.put(500,"No room availabe for the given advertisement id!!!");
           return message;
       }
       Customer retrievedcustomer=getCustomer(principal);

        RoomDto roomDto=roomMapper.selectAcceptedRoom(favourite.getRoomId());
        for (RoomDto eachRoomDto:roomDtoArrayList
             ) {
            if(eachRoomDto.getId().equals(roomDto.getId())){
                favourite.setCustomerId(retrievedcustomer.getId());
                Integer status=favouriteMapper.addFavourite(favourite);
                if(status<=0){
                    throw new RuntimeException("something went wrong!!!");
                }
                message.put(200,"room added to the favourite list successfully!!!!");
                return message;
            }
            }
        message.put(500,"something went wrong!!!");
        return message;
    }

    @Override
    public Map<Integer, String> RemoveRoomFromFavourite(RemoveRoomFromFavDto removeRoomFromFavDto,Principal principal) {
        Map<Integer,String> message=new HashMap<>();
        removeRoomFromFavDto.setCustomerId(getCustomer(principal).getId());
        Integer id=favouriteMapper.selectFavouriteByRoomAndUserWithAdvertisement(removeRoomFromFavDto);
        if(id==null){
           message.put(500,"something went wrong!!!!");
           return message;
        }
        Integer status=favouriteMapper.deleteRoomFromFav(id);
        if(status<=0){
            message.put(500,"something went wrong");
            return message;
        }
        message.put(200,"Remove room from the favourite list successfully!!!!");
        return message;
    }

    @Override
    public FavouriteDto getAllFavouriteRoom(Principal principal) {
        Customer customer=getCustomer(principal);
        return favouriteMapper.findAllFavouriteRoom(customer.getId());

    }

    private  Customer getCustomer(Principal principal){
        User retrievedUser=userMapper.findUserByEmail(principal.getName());
        Customer retrievedCustomer=customerMapper.findCustomerByUser(retrievedUser.getId());
        return retrievedCustomer;
    }
}
