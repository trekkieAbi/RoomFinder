package com.room.finder.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer extends User {
    private Integer id;
    private String phoneNumber;
    private Integer userId;
    private Integer roleid;

    public Customer(String phoneNumber, Integer userId, Integer roleid) {
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.roleid = roleid;
    }

}
