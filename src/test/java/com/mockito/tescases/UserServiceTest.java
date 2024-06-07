package com.mockito.tescases;

import com.mockito.test.exception.UserServiceException;
import com.mockito.test.model.User;
import com.mockito.test.repository.UserRepository;
import com.mockito.test.repository.UserRepositoryImpl;
import com.mockito.test.service.UserService;
import com.mockito.test.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class) //Extending Mockito
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    UserRepository userRepository;

    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        //userService = new UserServiceImpl(userRepository); //Manually creating UserService obj
        firstName = "Lishakar";
        lastName  = "Kumar";
        email = "test@test.com";
        password = "12345678";
        repeatPassword = "12345678";
    }
    @Test
    public void createNewUser() throws UserServiceException {
        //Stubbing random User object instead of the real parameter we pass below
        //Arrange
        Mockito.when(userRepository.saveUser(Mockito.any(User.class))).thenReturn(true); //Stubbing and return true
        //Actual Parameter
        User userObj = userServiceImpl.createUser(firstName,lastName,email,password,repeatPassword);
        System.out.println(userObj.toString());

        //Assert
        Assertions.assertEquals(userObj.getFirstName(),firstName); //This will not execute because we returned true
    }

}
