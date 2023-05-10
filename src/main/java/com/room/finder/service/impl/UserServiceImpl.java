package com.room.finder.service.impl;
import com.room.finder.dto.UserDto;
import com.room.finder.mapper.CustomerMapper;
import com.room.finder.mapper.LandlordMapper;
import com.room.finder.mapper.RoleMapper;
import com.room.finder.mapper.UserMapper;
import com.room.finder.model.Customer;
import com.room.finder.model.Landlord;
import com.room.finder.model.Role;
import com.room.finder.model.User;
import com.room.finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private LandlordMapper landlordMapper;

    private CustomerMapper customerMapper;

    private RoleMapper roleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String registerUser(UserDto userDto) {
        String message="";
        String name=checkUser(userDto);
        switch (name){
            case "Landlord":
                User retrievedLandlord=userDto.getUser();
                retrievedLandlord.setEnabled(true);
                retrievedLandlord.setPassword(passwordEncoder.encode(userDto.getUser().getPassword()));
                retrievedLandlord.setRoleName(roleMapper.findById(userDto.getRoleId()).getName());
                userMapper.saveUser(retrievedLandlord);
                Landlord landlord=new Landlord(userDto.getHouseNumber(),retrievedLandlord.getId(), userDto.getRoleId());
                landlordMapper.saveLandlord(landlord);
                message="landlord created successfully!!!";
                break;
            case "Customer":
                User retrievedCustomer=userDto.getUser();
                retrievedCustomer.setEnabled(true);
                userMapper.saveUser(retrievedCustomer);
                Customer customer=new Customer(userDto.getPhoneNumber(),retrievedCustomer.getId(), userDto.getRoleId());
                customerMapper.saveCustomer(customer);
                message="customer created successfully!!!";
        }
        return message;
    }

    private String checkUser(UserDto userDto){
        Role retrievedRole=roleMapper.findById(userDto.getRoleId());
        if(retrievedRole==null){
            throw new RuntimeException("Role not found for the given id");
        }
        if(retrievedRole.getName().equalsIgnoreCase("LANDLORD")){
            userDto.setPhoneNumber(null);
            return "Landlord";
        } else if (retrievedRole.getName().equalsIgnoreCase("customer")) {
            userDto.setHouseNumber(null);
            return "Customer";
        }else {
            throw new RuntimeException("Invalid role for the registration!!!");
        }
    }
}
