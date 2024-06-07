package com.mockito.test.service;

import com.mockito.test.exception.UserServiceException;
import com.mockito.test.model.User;
import com.mockito.test.repository.UserRepository;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository; //Repository object

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        boolean isUserCreated = userRepository.saveUser(user);
        if(!isUserCreated)
        {
            throw new UserServiceException("Could not create User");
        }
        return user;

    }
}
