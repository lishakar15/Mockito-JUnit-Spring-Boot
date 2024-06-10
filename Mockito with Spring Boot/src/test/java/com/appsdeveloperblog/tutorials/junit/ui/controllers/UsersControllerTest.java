package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.service.UsersService;
import com.appsdeveloperblog.tutorials.junit.shared.UserDto;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = {UsersController.class},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}) //Method 1
//@AutoConfigureMockMvc(addFilters = false) //Method 2
public class UsersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsersService usersService;  //Mocking Repository because we are testing only Controller

    @Test
    public void testCreateUser() throws Exception {

        //Arrange
        //Creating the request object to send as a RequestBody
        UserDetailsRequestModel userRequestModel = new UserDetailsRequestModel();
        userRequestModel.setFirstName("Andrew");
        userRequestModel.setLastName("Huberman");
        userRequestModel.setEmail("andrew@test.com");
        userRequestModel.setPassword("12345678");
        userRequestModel.setRepeatPassword("12345678");

        //Mock the UserService method and return our mock object
        UserDto userDto = new UserDto();
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setFirstName("Andrew");
        userDto.setLastName("Huberman");
        userDto.setEmail("huberman@test.com"); //Giving wrong email for testing
        userDto.setPassword("12345678");
        userDto.setEncryptedPassword("12345678");

        //Stubbing Service class createUser() to create a mock object
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        //Creating Request Builder to create request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/createUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequestModel))
                .contentType(MediaType.APPLICATION_JSON);

        //send the HTTP request to our Controller
        MvcResult mvcResults = mockMvc.perform(requestBuilder).andReturn();

        //Get the Api response
        String responseBodyStr = mvcResults.getResponse().getContentAsString();
        UserRest createdUser = new ObjectMapper().readValue(responseBodyStr,UserRest.class);

        //Assert
        assertEquals(createdUser.getFirstName(),userRequestModel.getFirstName());
        assertFalse(createdUser.getEmail().isEmpty());
        assertEquals(createdUser.getEmail(),userRequestModel.getEmail());//This will fail as email is not same

    }

}
