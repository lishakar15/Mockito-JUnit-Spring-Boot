package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = {UsersController.class},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}) //Method 1
//@AutoConfigureMockMvc(addFilters = false) //Method 2
public class UsersControllerTest {

    @Test
    public void testCreateUser()
    {
        //Implementation
    }

}
