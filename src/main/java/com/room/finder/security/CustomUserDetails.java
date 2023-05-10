package com.room.finder.security;

import com.room.finder.dto.UserDto;
import com.room.finder.mapper.RoleMapper;
import com.room.finder.mapper.Role_Authority_Mapper;
import com.room.finder.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
public class CustomUserDetails implements UserDetails {

   private UserDto userDto;

    private Role_Authority_Mapper roleAuthorityMapper;

    public CustomUserDetails(UserDto userDto, Role_Authority_Mapper roleAuthorityMapper) {
        this.userDto=userDto;
        this.roleAuthorityMapper = roleAuthorityMapper;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities=new ArrayList<>();
        Integer retrievedRoleId=userDto.getRoleId();
        ArrayList< Authority> authorities =roleAuthorityMapper.getAuthorityByRoleId(retrievedRoleId);
        if(authorities.isEmpty()){
            return grantedAuthorities;
        }
        grantedAuthorities.addAll(authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList()));

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return userDto.getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getUser().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
