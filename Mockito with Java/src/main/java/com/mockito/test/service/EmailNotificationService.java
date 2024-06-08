package com.mockito.test.service;

import com.mockito.test.model.User;

public interface EmailNotificationService {

    public void sendEmailToUser(User user);
}
