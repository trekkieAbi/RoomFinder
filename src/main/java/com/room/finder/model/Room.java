package com.room.finder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    private Integer id;
    private String name;
    private String imagePath;
    private String image;
    private boolean flag;
    private Integer advertisementId;

}
