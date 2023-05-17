package com.room.finder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room.finder.mapper.AuthorityMapper;
import com.room.finder.model.Authority;
import com.room.finder.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
    private AuthorityMapper authorityMapper;
    @Override
    public Map<Integer,String> createAuthority(Authority authority) throws Exception {
        Map<Integer,String> message=new HashMap<>();
        Optional<Authority> authority2=authorityMapper.findByName(authority);
        if(authority2.isPresent()) {
            message.put(500,"authority with the given name already exists!!!");
            return message;
        }
        authority.setName(authority.getName().toLowerCase());
        int status=authorityMapper.saveAuthority(authority);
        if(status>0){
            message.put(200,"authority added successfully!!!");
            return message;
        }
        message.put(500,"something went wrong");
        return null;
    }

    @Override
    public Integer deleteAuthority(Integer id) throws Exception {
        Authority retrievedAuthority=authorityMapper.findById(id);
        if(retrievedAuthority==null){
            return 0;
        }
        authorityMapper.deleteAuthority(id);
        return 1;
    }

    @Override
    public Integer updateAuthority(Authority authority) {
        return null;
    }

    @Override
    public List<Authority> getAllAuthority() {
        return null;
    }

    @Override
    public Authority getAuthorityByKey(Integer authId) {
        return null;
    }

}
