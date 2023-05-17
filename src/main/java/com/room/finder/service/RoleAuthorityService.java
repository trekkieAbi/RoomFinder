package com.room.finder.service;

import java.util.ArrayList;

import com.room.finder.model.Authority;
import com.room.finder.model.RoleAuthority;

public interface RoleAuthorityService {
	  Integer createRoleAuthority(RoleAuthority roleAuthority);
	    Integer deleteRoleAuthority(Integer roleAuthorityId);
	    ArrayList<Authority> getAllAuthorityByRole(Integer roleId) throws Exception;
}
