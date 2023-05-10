package com.room.finder.dto;

import com.room.finder.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDto {
    private User user;
    private String phoneNumber;
    private Integer houseNumber;
    private Integer roleId;

}
