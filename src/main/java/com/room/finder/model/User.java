package com.room.finder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean isEnabled;
    private String roleName;
    public User(String name, String email, String password, boolean isEnabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public User(Integer id, String name, String email, String password, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }
}
