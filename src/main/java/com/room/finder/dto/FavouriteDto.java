package com.room.finder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FavouriteDto {
    private Integer id;
    private List<AdvertisementDto> advertisementDto;
}
