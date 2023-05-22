package com.room.finder.service.impl;

import com.room.finder.mapper.AuthorityMapper;
import com.room.finder.mapper.RoleMapper;
import com.room.finder.mapper.RoleAuthorityMapper;
import com.room.finder.model.Authority;
import com.room.finder.model.Role;
import com.room.finder.model.RoleAuthority;
import com.room.finder.service.RoleAuthorityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService {
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public Integer createRoleAuthority(RoleAuthority roleAuthority) {
        Integer resultValue=0;
        if(checkWhetherRoleAuthorityExists(roleAuthority)) {
            if(roleAuthorityMapper.checkWhetherDuplicateRoleAuthority(roleAuthority)==null) {
                resultValue=roleAuthorityMapper.saveRoleAuthority(roleAuthority);
            }else {
                return resultValue;
            }
        }
        return resultValue;
    }

    @Override
    public Integer deleteRoleAuthority(Integer roleAuthorityId) {
        Integer resultValue=0;
        RoleAuthority retrievedRoleAuthority=roleAuthorityMapper.getRoleAuthorityById(roleAuthorityId);
        if(retrievedRoleAuthority!=null) {
            resultValue=roleAuthorityMapper.deleteRoleAuthority(retrievedRoleAuthority.getId());

        }
        return resultValue;

    }

    @Override
    public ArrayList<Authority> getAllAuthorityByRole(Integer roleId) throws Exception {
        Role retrievedRole=roleMapper.findById(roleId);
        if(retrievedRole!=null) {
            ArrayList<Authority> authorities=roleAuthorityMapper.getAuthorityByRoleId(retrievedRole.getId());
            return authorities;
        }
        else {
            throw new Exception("No role found!!!");
        }
    }
    private Boolean checkWhetherRoleAuthorityExists(RoleAuthority roleAuthority) {
        Boolean status=false;
        Role role=roleMapper.findById(roleAuthority.getRoleId());
        Authority authority=authorityMapper.findById(roleAuthority.getAuthorityId());
        if(role!=null && authority!=null) {
            status=true;
        }
        return status;
    }

}
