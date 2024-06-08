package com.mockito.tescases;

import com.mockito.test.exception.EmailServiceException;
import com.mockito.test.exception.UserServiceException;
import com.mockito.test.model.User;
import com.mockito.test.repository.UserRepository;
import com.mockito.test.service.EmailNotificationService;
import com.mockito.test.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Extending Mockito
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    UserRepository userRepository;
    @Mock
    EmailNotificationService emailNotificationService;

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
        //Arrange
        when(userRepository.saveUser(any(User.class))).thenReturn(true); //Stubbing and return true
        //Actual Parameter
        User userObj = userServiceImpl.createUser(firstName,lastName,email,password,repeatPassword);
        System.out.println(userObj.toString());

        //Assert
        Assertions.assertEquals(userObj.getFirstName(),firstName); //This will not execute because we returned true

        //Verify the number of times mock method getting called
        Mockito.verify(userRepository, times(1)).saveUser(any(User.class));// This is a one seperate testcase
    }

    @Test
    public void testCreateUser_whenSaveUser_throwsException_ThenThrowsUserServiceException() throws UserServiceException {
        when(userRepository.saveUser(any(User.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(UserServiceException.class, () -> {
            userServiceImpl.createUser(firstName, lastName, email, password, repeatPassword);
        });
    }
    @Test
    public void tesCreateUser_whenEmailServiceTrownException_throwUserServiceException()
        {
            //Arrange
            when(userRepository.saveUser(any(User.class))).thenReturn(true); //Stubbing and return true

            //when(emailNotificationService.sendEmailToUser(any(User.class)))
            //This below is for throwing Exception to void method
            doThrow(EmailServiceException.class).when(emailNotificationService).sendEmailToUser(any(User.class));
            //Do nothing when method is called
            doNothing().when(emailNotificationService).sendEmailToUser(any(User.class));

            //Act & Assert
            Assertions.assertThrows(UserServiceException.class,()->{
                userServiceImpl.createUser(firstName,lastName,email,password,repeatPassword);
            },"Should have thrown UserServiceException instead");

            //Verify how many times sendEmail method is getting called
            verify(emailNotificationService,times(1)).sendEmailToUser(any(User.class));
        }
        @Test
        public void testCallRealMethod() throws UserServiceException {
            //Arrange
            when(userRepository.saveUser(any(User.class))).thenReturn(true); //Stubbing and return true
            //Calling the original method
            doCallRealMethod().when(emailNotificationService).sendEmailToUser(any(User.class));

            //Act
            userServiceImpl.createUser(firstName,lastName,email,password,repeatPassword);

            //Assert
            verify(emailNotificationService,times(1)).sendEmailToUser(any(User.class));

        }
}
