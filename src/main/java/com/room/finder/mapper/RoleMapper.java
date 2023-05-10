package com.room.finder.mapper;

import com.room.finder.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface RoleMapper {
    Integer saveRole(Role role);
    Integer updateRole(Role role);
    Role findById(Integer id);
    Role findByName(String name);

}
