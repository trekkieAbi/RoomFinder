package com.room.finder.controller;

import com.room.finder.dto.LoginDto;
import com.room.finder.dto.UserDto;
import com.room.finder.mapper.RoleMapper;
import com.room.finder.mapper.Role_Authority_Mapper;
import com.room.finder.mapper.UserMapper;
import com.room.finder.model.User;
import com.room.finder.security.CustomUserDetails;
import com.room.finder.security.CustomUserDetailsService;
import com.room.finder.service.UserService;
import com.room.finder.util.JwtHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private CustomUserDetailsService customUserDetailsService;
    private JwtHelper jwtHelper;

    private UserMapper userMapper;

    private RoleMapper roleMapper;
    private Role_Authority_Mapper roleAuthorityMapper;



    private AuthenticationManager authenticationManager;
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    ResponseEntity<String> registerUser(@RequestBody UserDto userDto){
        String message=userService.registerUser(userDto);
        return  ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) throws Exception {
        String userName = loginDto.getUserEmail();
        String password = loginDto.getPassword();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        authenticate(userName, password, userDetails.getAuthorities());
        User user=userMapper.findUserByEmail(userName);
        UserDto retrievedUser =getUserDto(user);
        if(user.isEnabled()) {
            String jwtToken = this.jwtHelper
                    .generateToken(new CustomUserDetails(retrievedUser, roleAuthorityMapper));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtToken);
            return ResponseEntity.ok().headers(headers).body("Token generated successfully!!!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
    }
    private void authenticate(String userName, String password, Collection<? extends GrantedAuthority> authorities)
            throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userName, password, authorities);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (DisabledException e) {
            throw new Exception("user is disabled!!!");
        }
    }
    private UserDto getUserDto(User user){
       UserDto userDto=new UserDto();
       userDto.setUser(user);
       userDto.setRoleId(roleMapper.findByName(user.getName()).getId());
       return userDto;
    }


}
