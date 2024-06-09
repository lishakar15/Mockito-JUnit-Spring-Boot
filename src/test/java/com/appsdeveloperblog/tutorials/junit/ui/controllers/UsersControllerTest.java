package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.service.UsersService;
import com.appsdeveloperblog.tutorials.junit.ui.request.UserDetailsRequestModel;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@WebMvcTest(controllers = {UsersController.class},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}) //Method 1
//@AutoConfigureMockMvc(addFilters = false) //Method 2
public class UsersControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UsersService usersService;

    @Test
    public void testCreateUser() throws Exception {
        //Arrange
        //Let's test createUser() POST method in UserController
        UserDetailsRequestModel userRequestModel = new UserDetailsRequestModel();
        userRequestModel.setFirstName("Andrew");
        userRequestModel.setLastName("Huberman");
        userRequestModel.setEmail("andrew@ysd.com");
        userRequestModel.setPassword("12345678");
        userRequestModel.setRepeatPassword("12345678");

        //Creating Request Builder to create request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/createUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestModel))
                .contentType(MediaType.APPLICATION_JSON);

        //send the HTTP request to our Controller
        MvcResult mvcResults = mockMvc.perform(requestBuilder).andReturn();

        //Get the API response
        String responseBodyStr = mvcResults.getResponse().getContentAsString();
        UserRest createdUser = new ObjectMapper().readValue(responseBodyStr, UserRest.class);

        //Assert
        assertEquals(createdUser.getFirstName(),userRequestModel.getFirstName());
        assertFalse(createdUser.getEmail().isEmpty());
    }

}
