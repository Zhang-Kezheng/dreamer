package com.zkz.dreamer.impl;

import com.zkz.dreamer.entity.User;
import com.zkz.dreamer.security.SystemUser;
import com.zkz.dreamer.security.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public SystemUser loadUserByUsername(String username) {
        User user=new User();
        user.setUsername("123");
        user.setId("123");
        return user;
    }
}
