package com.mockito.test.service;

import com.mockito.test.exception.UserServiceException;
import com.mockito.test.model.User;
import com.mockito.test.repository.UserRepository;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository; //Repository object

    private EmailNotificationService emailNotificationService;

    public UserServiceImpl(UserRepository userRepository,EmailNotificationService emailNotificationService) {
        this.userRepository = userRepository;
        this.emailNotificationService = emailNotificationService;
    }

    public User createUser(String firstName, String lastName, String email, String password,
                           String repeatPassword) throws UserServiceException {

        if(firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if(lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("User's last name is empty");
        }
        //Save User
        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());
        try {
            boolean isUserCreated = userRepository.saveUser(user);
            if(!isUserCreated)
            {
                throw new UserServiceException("Could not create User");
            }
            else
            {
                System.out.println("Users created successfully");
                emailNotificationService.sendEmailToUser(user); //Send email to user
            }
        }
        catch (RuntimeException ex)
        {
            throw new UserServiceException("Exception occurred while saving user.");
        }
        return user;
    }
}
