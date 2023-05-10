package com.room.finder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
@Getter
@Setter
public class Advertisement {
    private Integer id;
    private String title;
    private String description;
    private Date roomAvailableDate;
    private String address;
    private Integer rent;
    private Integer landlordId;
    private String status;




}
