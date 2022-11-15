package com.zkz.dreamer.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
public class DefaultUserDetailsService implements AbstractUserDetailService {
    @Resource
    private UserInfoService<? extends SystemUser> userInfoService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userInfoService.loadUserByUsername(username);
    }
}
