package com.room.finder.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.room.finder.model.Authority;
import com.room.finder.service.AuthorityService;

@RestController
@RequestMapping("/authority")
public class AuthorityController {
	  @Autowired
	    private AuthorityService authorityService;
	    @RequestMapping(value = "/create",method = RequestMethod.POST)
	    ResponseEntity<Map<Integer,String>> createAuthorityController(@RequestBody Authority authority) throws Exception {
	        Map<Integer,String> message=authorityService.createAuthority(authority);
	        if(message.containsKey(200)){
	            return ResponseEntity.status(HttpStatus.OK).body(message);
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
	    }
	    @RequestMapping(value = "/delete/{authorityId}",method = RequestMethod.DELETE)
	    ResponseEntity<String> deleteAuthorityController(@PathVariable Integer authorityId) throws Exception {
	        Integer message=authorityService.deleteAuthority(authorityId);
	        if(message==1){
	            return ResponseEntity.status(HttpStatus.OK).body("Authority deleted successfully");
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
	    }
}
