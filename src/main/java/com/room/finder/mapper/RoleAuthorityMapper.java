package com.room.finder.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.room.finder.model.Authority;
import com.room.finder.model.RoleAuthority;
@Mapper
public interface RoleAuthorityMapper {
	  Integer saveRoleAuthority(RoleAuthority roleAuthority);
	   Integer deleteRoleAuthority(Integer roleAuthorityId);
	   ArrayList<Authority> getAuthorityByRoleId(Integer roleId);
	  RoleAuthority  checkWhetherDuplicateRoleAuthority(RoleAuthority roleAuthority);
	   RoleAuthority getRoleAuthorityById(Integer id);
}
