package com.room.finder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Landlord extends User{
    private Integer id;
    private Integer houseNumber;
    private Integer userId;
    private Integer roleId;

    public Landlord(Integer houseNumber, Integer userId, Integer roleId) {
        this.houseNumber = houseNumber;
        this.userId = userId;
        this.roleId = roleId;

    }
}
