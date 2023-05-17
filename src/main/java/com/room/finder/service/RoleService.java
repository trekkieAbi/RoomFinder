package com.room.finder.service;

import java.util.Map;

import com.room.finder.model.Role;

public interface RoleService {
	  Map<Integer,String> createRole(Role role);
	    Map<Integer,String> updateRole(Role role);
	    Role findById(Integer roleId);

}
