package com.mockito.tescases;

import com.mockito.test.model.User;
import com.mockito.test.service.EmailNotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class) //Extending Mockito
public class CallRealMethodTest {

    @Mock
    EmailNotificationServiceImpl emailNotificationServiceImpl;

    @Test
    public void testCallRealMethod()
    {
        doCallRealMethod().when(emailNotificationServiceImpl).sendEmailToUser(any(User.class));
        emailNotificationServiceImpl.sendEmailToUser(new User());
    }
}
