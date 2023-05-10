package com.room.finder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RoleAuthority {
    private Integer id;
    private Integer roleId;
    private Integer authorityId;

}
