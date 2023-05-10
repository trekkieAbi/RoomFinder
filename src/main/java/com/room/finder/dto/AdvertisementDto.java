package com.room.finder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class AdvertisementDto {
    private Integer id;
    private String title;
    private String description;
    private Date roomAvailableDate;
    private String address;
    private Integer rent;

}
