package com.room.finder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Moderator extends User{
    private Integer id;
    private Integer userId;
    private Integer roleId;
    public Moderator() {

    }

    public Moderator(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
