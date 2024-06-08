package com.mockito.test.service;

import com.mockito.test.model.User;

public class EmailNotificationServiceImpl implements EmailNotificationService {
    @Override
    public void sendEmailToUser(User user) {
        //Send email to users
        System.out.println("Email has been sent to user "+user.getFirstName());
    }
}
