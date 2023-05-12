package com.room.finder.mapper;

import com.room.finder.model.Authority;
import com.room.finder.model.RoleAuthority;
import org.apache.ibatis.annotations.Mapper;


import java.util.ArrayList;
@Mapper
public interface Role_Authority_Mapper {
   Integer saveRoleAuthority(RoleAuthority roleAuthority);
   Integer deleteRoleAuthority(Integer roleAuthorityId);
   ArrayList<Authority> getAuthorityByRoleId(Integer roleId);
  RoleAuthority  checkWhetherDuplicateRoleAuthority(RoleAuthority roleAuthority);
    RoleAuthority getRoleAuthorityById(Integer id);
}
