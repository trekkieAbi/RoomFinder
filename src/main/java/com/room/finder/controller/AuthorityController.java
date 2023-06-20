package com.room.finder.controller;

import com.room.finder.mapper.AuthorityMapper;
import com.room.finder.model.Authority;
import com.room.finder.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private AuthorityMapper authorityMapper;
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    ResponseEntity<Map<Integer,String>> createAuthorityController(@RequestBody Authority authority) throws Exception {
        Map<Integer,String> message=authorityService.createAuthority(authority);
        if(message.containsKey(200)){
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
    @RequestMapping(value = "/delete/{authorityId}",method = RequestMethod.POST)
    ResponseEntity<String> deleteAuthorityController(@PathVariable Integer authorityId) throws Exception {
        Integer message=authorityService.deleteAuthority(authorityId);
        if(message==1){
            return ResponseEntity.status(HttpStatus.OK).body("Authority deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
    }

    @GetMapping(value = "/readAll")
    ArrayList<Authority> getAllAuthority(){
        return authorityMapper.getAllAuthority();
    }



}
