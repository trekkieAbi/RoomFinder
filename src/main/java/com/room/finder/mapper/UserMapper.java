package com.room.finder.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.room.finder.model.User;
@Mapper
public interface UserMapper {
	Integer saveUser(User user);
	   Integer deleteUser(Integer id);
	    Integer updateUser(User user);
	    User findUserByEmail(String email);
	    User findUserById(Integer id);
}
