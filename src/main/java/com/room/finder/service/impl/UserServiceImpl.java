package com.room.finder.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.room.finder.dto.UserDto;
import com.room.finder.mapper.CustomerMapper;
import com.room.finder.mapper.LandlordMapper;
import com.room.finder.mapper.ModeratorMapper;
import com.room.finder.mapper.RoleMapper;
import com.room.finder.mapper.UserMapper;
import com.room.finder.model.Customer;
import com.room.finder.model.Landlord;
import com.room.finder.model.Moderator;
import com.room.finder.model.Role;
import com.room.finder.model.User;
import com.room.finder.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	 private UserMapper userMapper;
	@Autowired
	private LandlordMapper landlordMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
   private RoleMapper roleMapper;
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    @Autowired
	    private ModeratorMapper moderatorMapper;
	    @Override
	    public String registerUser(UserDto userDto) {
	        String message="";
	        String name=checkUser(userDto);
	        switch (name){
	            case "Landlord":
	                User retrievedLandlord=userDto.getUser();
	                retrievedLandlord.setEnabled(true);
	                retrievedLandlord.setPassword(passwordEncoder.encode(userDto.getUser().getPassword()));
	                retrievedLandlord.setRoleName(roleMapper.getById(userDto.getRoleId()).getName());
	                userMapper.saveUser(retrievedLandlord);
	                Landlord landlord=new Landlord(userDto.getHouseNumber(),retrievedLandlord.getId(), userDto.getRoleId());
	                landlordMapper.saveLandlord(landlord);
	                message="landlord created successfully!!!";
	                break;
	            case "Customer":
	                User retrievedCustomer=userDto.getUser();
	                retrievedCustomer.setEnabled(true);
	                retrievedCustomer.setRoleName(roleMapper.getById(userDto.getRoleId()).getName());
	                userMapper.saveUser(retrievedCustomer);
	                Customer customer=new Customer(userDto.getPhoneNumber(),retrievedCustomer.getId(),userDto.getRoleId());
	                customerMapper.saveCustomer(customer);
	                message="customer created successfully!!!";
	                break;
	            case "Moderator":
	            	User retrievedModerator=userDto.getUser();
	            	retrievedModerator.setEnabled(true);
	            	retrievedModerator.setPassword(passwordEncoder.encode(retrievedModerator.getPassword()));
	            	String moderatorName=roleMapper.getById(userDto.getRoleId()).getName();
	    			retrievedModerator.setRoleName(moderatorName);
	            	userMapper.saveUser(retrievedModerator);
	            	Moderator moderator=new Moderator(retrievedModerator.getId(),userDto.getRoleId());
	            	moderatorMapper.saveModerator(moderator);
	            	message="moderator created successfully";
	            	break;
	            	
	            }
	        return message;
	    }

	    private String checkUser(UserDto userDto){
	        Role retrievedRole=roleMapper.getById(userDto.getRoleId());
	        if(retrievedRole==null){
	            throw new RuntimeException("Role not found for the given id");
	        }
	        if(retrievedRole.getName().equalsIgnoreCase("ROLE_LANDLORD")){
	            userDto.setPhoneNumber(null);
	            return "Landlord";
	        } else if (retrievedRole.getName().equals("ROLE_CUSTOMER")) {
	            userDto.setHouseNumber(null);
	            return "Customer";
	        }else if(retrievedRole.getName().equals("ROLE_MODERATOR")){
	        	return "Moderator";
	        	
	        }
	        
	        else {
	            throw new RuntimeException("Invalid role for the registration!!!");
	        }
	    }
	

}
