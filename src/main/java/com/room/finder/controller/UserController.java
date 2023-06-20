package com.room.finder.controller;

import com.room.finder.dto.LoginDto;
import com.room.finder.dto.UserDto;
import com.room.finder.mapper.RoleMapper;
import com.room.finder.mapper.RoleAuthorityMapper;
import com.room.finder.mapper.UserMapper;
import com.room.finder.model.Role;
import com.room.finder.model.User;
import com.room.finder.security.CustomUserDetails;
import com.room.finder.security.CustomUserDetailsService;
import com.room.finder.service.UserService;
import com.room.finder.util.JwtHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    ResponseEntity<String> registerUser(@Valid @RequestBody  UserDto userDto,BindingResult bindingResult) throws MethodArgumentNotValidException {
        if(bindingResult.hasErrors()){
            throw new MethodArgumentNotValidException(null,bindingResult);
        }

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
       Optional<Role> retrievedRole=roleMapper.findByName(user.getRoleName());
       userDto.setRoleId(retrievedRole.get().getId());
       return userDto;
    }


}
