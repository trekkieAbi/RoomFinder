package com.room.finder.service.impl;
import com.room.finder.mapper.RoleMapper;
import com.room.finder.model.Role;
import com.room.finder.model.RoleCheck;
import com.room.finder.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
    private  RoleMapper roleMapper;


    @Override
    public Map<Integer, String> createRole(Role role) {
        Map<Integer,String> message=new HashMap<>();
        Optional<Role> retrievedRole=roleMapper.findByName(role.getName());
        if(retrievedRole.isPresent()){
            message.put(500,"Role with the given name already exists!!");
            return message;

        }
        if(role.getName().equalsIgnoreCase(RoleCheck.ROLE_CUSTOMER.toString())||role.getName().equalsIgnoreCase(RoleCheck.ROLE_MODERATOR.toString())||
        role.getName().equalsIgnoreCase(RoleCheck.ROLE_LANDLORD.toString())){
            role.setName(role.getName().toUpperCase());
        }else{
            message.put(500,"Invalid role name");
        }
        int result=roleMapper.saveRole(role);
       message.put(200,"role created successfully!!!!!");
        return message;
    }

    @Override
    public Map<Integer, String> updateRole(Role role) {
        Map<Integer, String> message = new HashMap<>();
        Role retrievedRole = roleMapper.findById(role.getId());
        if (retrievedRole == null) {
            message.put(500, "Role with the given id doesnot exists!!!");
            return message;
        }
        if (!role.getName().isEmpty()) {
            Optional<Role> instantRole = roleMapper.findByName(role.getName().toUpperCase());//check whether the given role name already exists in the db
            if (instantRole != null) {
                if (!instantRole.equals(retrievedRole)) {//check whether the instant role is equal to the retrievedRole or not
                    message.put(500,"role with the given name already exists which is other than the retrieved role");
                }
            }
            role.setName(role.getName().toUpperCase());
            Integer result=roleMapper.saveRole(role);
           if(result>0) {
               message.put(200, "role updated successfully!!!");
           }else {
               message.put(500, "Something went wrong!!!");
           }
            return message;
        }
        return message;
    }

    @Override
    public Role findById(Integer roleId) {
        return roleMapper.findById(roleId);
    }
}
