package com.room.finder.controller;

import com.room.finder.model.Role;
import com.room.finder.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
   ResponseEntity<Map<Integer,String>> createRoleController(@RequestBody Role role){
        Map<Integer,String> message=roleService.createRole(role);
        if(message.containsKey(200)){
          return ResponseEntity.status(HttpStatus.OK).body(message);
        }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    ResponseEntity<Map<Integer,String>> updateRoleController(@RequestBody Role role){
        Map<Integer,String> message=roleService.updateRole(role);
        if(message.containsKey(200)){
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @RequestMapping(value = "/read/{roleId}",method = RequestMethod.GET)
    ResponseEntity<?> readRoleController(@PathVariable Integer role){
        Role retrievedRole=roleService.findById(role);
        if(retrievedRole!=null){
            return ResponseEntity.status(HttpStatus.OK).body(retrievedRole);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No role found!!!");
    }


}
