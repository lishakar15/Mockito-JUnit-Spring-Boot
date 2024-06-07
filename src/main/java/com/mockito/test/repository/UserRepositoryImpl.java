package com.mockito.test.repository;

import com.mockito.test.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository{

    Map<Integer,User> userMap = new HashMap<>();
    @Override
    public boolean saveUser(User user) {
        boolean isSaved = false;
        if(!userMap.containsKey(user.getId()))
        {
            userMap.put(Integer.getInteger(user.getId()),user);
            isSaved = true;
        }
        return isSaved;
    }
}
