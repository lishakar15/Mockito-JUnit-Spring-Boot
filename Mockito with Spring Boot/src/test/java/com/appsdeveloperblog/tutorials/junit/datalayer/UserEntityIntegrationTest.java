package com.appsdeveloperblog.tutorials.junit.datalayer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;

@DataJpaTest
public class UserEntityIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;

    private static UserEntity userInit;

    @BeforeAll
    public static void init()
    {
        //Creating this object for duplicate id persist test
        userInit = new UserEntity();
        userInit.setFirstName("John");
        userInit.setLastName("Rocky");
        userInit.setEmail("john@testmail.com");
        userInit.setEncryptedPassword("12345");
        userInit.setUserId("1");
    }

    @Test
    public void testEntityPersist()
    {
        //Arrange
        UserEntity user = new UserEntity();
        user.setFirstName("John");
        user.setLastName("Rocky");
        user.setEmail("john@testmail.com");
        user.setEncryptedPassword("12345");
        user.setUserId("1");

        //Assert & Act
        UserEntity createdUser = testEntityManager.persistAndFlush(user);

        Assertions.assertEquals(createdUser.getFirstName(),user.getFirstName());
        Assertions.assertTrue(createdUser.getId() > 0);
        //Trying to persist duplicate user ID
        Assertions.assertThrows(PersistenceException.class,()->{
            testEntityManager.persistAndFlush(userInit);   //It throws PersistenceException
        });

    }

}
