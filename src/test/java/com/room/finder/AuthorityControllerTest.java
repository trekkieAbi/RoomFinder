package com.room.finder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.room.finder.controller.AuthorityController;
import com.room.finder.model.Authority;
import com.room.finder.service.AuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.zalando.problem.Problem.*;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import static org.hamcrest.CoreMatchers.is;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorityService authorityService;
    @Autowired
    private ObjectMapper objectMapper;

    private List<Authority> authorityList;
@BeforeEach
    void setUp(){
        this.authorityList=new ArrayList<>();
        authorityList.add(new Authority(1,"add_user"));
        authorityList.add(new Authority(2,"remove_user"));
        objectMapper.registerModules(new ProblemModule());
    }

    public void givenAuthorityObject_WhenCreateAuthority_thenReturnMap(){

    }
    @Test
    void shouldFetchAllAuthority() throws Exception {
    given(authorityService.getAllAuthority()).willReturn(authorityList);
    this.mockMvc.perform(get("/authority/readAll"))
            .andExpect(status().isOk());
    }


}
