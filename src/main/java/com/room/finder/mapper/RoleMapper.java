package com.room.finder.mapper;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import com.room.finder.model.Role;

@Mapper
public interface RoleMapper {
    Integer saveRole(Role role);
    Integer updateRole(Role role);
    Role findById(Integer id);
    Optional<Role> findByName(String roleName);

}
