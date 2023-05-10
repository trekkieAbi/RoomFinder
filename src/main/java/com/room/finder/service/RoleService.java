package com.room.finder.service;

import com.room.finder.model.Role;

import java.util.Map;

public interface RoleService {
    Map<Integer,String> createRole(Role role);
    Map<Integer,String> updateRole(Role role);
    Role findById(Integer roleId);

}
