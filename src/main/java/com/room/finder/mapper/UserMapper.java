package com.room.finder.mapper;

import com.room.finder.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
   Integer saveUser(User user);
   Integer deleteUser(Integer id);
    Integer updateUser(User user);
    User findUserByEmail(String email);
    User findUserById(Integer id);
    User findUserByName(String name);
}
