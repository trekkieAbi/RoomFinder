package com.room.finder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static  org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import com.room.finder.model.Role;
import com.room.finder.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RoleControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    List<Role> roleList=new ArrayList<>();


    private Role role;
    @MockBean
    private static RoleServiceImpl roleServiceImpl;

    private Role role1;

    private Role role2;

    @BeforeEach
    public void setupTestSuit() {
        role1 = new Role(1, "ADMIN");
        role2 = new Role(2, "NORMAl");
        roleList.add(role1);
        roleList.add(role2);
    }
    @AfterEach
    void tearDown(){

    }
@Test
    void getRoleDetails() throws Exception {

        String URL_TEMPLATE="/role/read/1";
        OngoingStubbing ongoingStubbing=when(roleServiceImpl.findById(role1.getId()));
        ongoingStubbing=ongoingStubbing.thenReturn(role1);
        String expectedjson=mapToJson(role1);
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .get(URL_TEMPLATE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult=mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response=mvcResult.getResponse();
        String outputJson=response.getContentAsString();
        assertThat(outputJson).isEqualTo(expectedjson);
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }





@Test
    public void testCreateRole() throws Exception {

        String URL_TEMPLATE="/role/create";
        Role role1=new Role();
        role1.setId(1);
        role1.setName("ROLE_NORMAL");
        Map<Integer,String> expectedResult=new HashMap<>();
        expectedResult.put(200,"role created successfully!!!!!");
        String inputInJson=this.mapToJson(role1);
    OngoingStubbing ongoingStubbing=when(roleServiceImpl.createRole(role1));
    ongoingStubbing=ongoingStubbing.thenReturn(expectedResult);

    RequestBuilder requestBuilder= MockMvcRequestBuilders
            .post(URL_TEMPLATE)
            .accept(MediaType.APPLICATION_JSON).content(inputInJson)
            .contentType(MediaType.APPLICATION_JSON);
    MvcResult mvcResult=mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response =mvcResult.getResponse();
    assertEquals(HttpStatus.OK.value(),response.getStatus());

    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }


}
