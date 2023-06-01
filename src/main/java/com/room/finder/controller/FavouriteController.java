package com.room.finder.controller;

import com.room.finder.dto.FavouriteDto;
import com.room.finder.dto.RemoveRoomFromFavDto;
import com.room.finder.model.Favourite;
import com.room.finder.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/favourite")
public class FavouriteController {
    @Autowired
    private FavouriteService favouriteService;
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('add_room_to_favourite')")
    ResponseEntity<Map<Integer,String>> addFavouriteController(@RequestBody Favourite favourite, Principal principal){
        Map<Integer,String> message=favouriteService.AddToFavourite(favourite,principal);
        if(message.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }



    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('remove_room_froom_favourite')")
    ResponseEntity<Map<Integer,String>> removeFavouriteController(@RequestBody RemoveRoomFromFavDto removeRoomFromFavDto, Principal principal){
        Map<Integer,String> message=favouriteService.RemoveRoomFromFavourite(removeRoomFromFavDto,principal);
        if(message.containsKey(500)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    @RequestMapping(value = "/view-favourite",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('view_all_favourite_room')")
    ResponseEntity<FavouriteDto> viewAllFavouriteController(Principal principal){
        FavouriteDto favouriteDto=favouriteService.getAllFavouriteRoom(principal);
        return ResponseEntity.status(HttpStatus.OK).body(favouriteDto);
    }
}
