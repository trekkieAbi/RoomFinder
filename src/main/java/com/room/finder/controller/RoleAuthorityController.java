package com.room.finder.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.room.finder.model.Authority;
import com.room.finder.model.RoleAuthority;
import com.room.finder.service.RoleAuthorityService;


@RestController
@RequestMapping("/role-authority")
public class RoleAuthorityController {
    @Autowired
    private RoleAuthorityService authorityService;
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    ResponseEntity<String> createRoleAuthorityController(@RequestBody RoleAuthority roleAuthority){
        Integer resultValue=authorityService.createRoleAuthority(roleAuthority);
        if(resultValue>0){
            return ResponseEntity.status(HttpStatus.OK).body("role-authority created successfully!!!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!!");
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    ResponseEntity<String> deleteAuthorityController(@PathVariable Integer id){
        Integer resultValue=authorityService.deleteRoleAuthority(id);
        if(resultValue>0){
            return ResponseEntity.status(HttpStatus.OK).body("role-authority deleted successfully!!!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!!");
    }
    @RequestMapping(value = "/read/{roleId}",method = RequestMethod.GET)
    ResponseEntity<ArrayList<Authority>> getAuthorityByRoleController(@PathVariable Integer roleId) throws Exception {
        ArrayList<Authority> authorities =authorityService.getAllAuthorityByRole(roleId);
        if(!authorities.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(authorities);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
