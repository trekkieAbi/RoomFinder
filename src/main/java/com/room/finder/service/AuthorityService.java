package com.room.finder.service;

import com.room.finder.model.Authority;

import java.util.List;
import java.util.Map;

public interface AuthorityService {
    Map<Integer,String> createAuthority(Authority authority)throws Exception;
    Integer deleteAuthority(Integer id)throws Exception;
    Integer updateAuthority(Authority authority);
    List<Authority> getAllAuthority();
    Authority getAuthorityByKey(Integer authId);
}
