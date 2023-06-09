package com.room.finder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.room.finder.dto.UserDto;
import com.room.finder.mapper.CustomerMapper;
import com.room.finder.mapper.LandlordMapper;
import com.room.finder.mapper.ModeratorMapper;
import com.room.finder.mapper.RoleAuthorityMapper;
import com.room.finder.mapper.UserMapper;
import com.room.finder.model.Customer;
import com.room.finder.model.Landlord;
import com.room.finder.model.Moderator;
import com.room.finder.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    private UserMapper  userMapper;
	@Autowired
    private ModeratorMapper moderatorMapper;
	
@Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
@Autowired

    private LandlordMapper landlordMapper;
@Autowired
    private  CustomerMapper customerMapper;
@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User retrievedUser=userMapper.findUserByEmail(username);
        if(retrievedUser==null){
            throw new UsernameNotFoundException("user not found with username "+username);
        }
        UserDto userDto=new UserDto();
        switch (retrievedUser.getRoleName()){
            case "ROLE_MODERATOR":
                Moderator moderator=moderatorMapper.findModeratorByUser(retrievedUser.getId());
                userDto.setUser(retrievedUser);
                userDto.setRoleId(moderator.getRoleId());
                break;
            case "ROLE_LANDLORD":
                Landlord landlord=landlordMapper.findLandlordByUser(retrievedUser.getId());
                userDto.setUser(retrievedUser);
                userDto.setRoleId(landlord.getRoleId());
                break;
            case "ROLE_CUSTOMER":
                Customer customer=customerMapper.findCustomerByUser(retrievedUser.getId());
                userDto.setUser(retrievedUser);
                userDto.setRoleId(customer.getRoleid());
                break;

        }
        CustomUserDetails customUserDetails=new CustomUserDetails(userDto,roleAuthorityMapper);
        return customUserDetails;
    }
}
