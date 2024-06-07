package com.mockito.test.service;

import com.mockito.test.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    /**
     * 
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param repeatPassword
     * @return
     */
    @Override
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword) {

        if(firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if(lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("User's last name is empty");
        }

        return new User(firstName, lastName, email, UUID.randomUUID().toString());

    }
}
