package com.room.finder.service;

import com.room.finder.model.Authority;
import com.room.finder.model.RoleAuthority;

import java.util.ArrayList;

public interface RoleAuthorityService {
    Integer createRoleAuthority(RoleAuthority roleAuthority);
    Integer deleteRoleAuthority(Integer roleAuthorityId);
    ArrayList<Authority> getAllAuthorityByRole(Integer roleId) throws Exception;


}
