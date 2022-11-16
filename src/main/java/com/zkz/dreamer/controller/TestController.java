package com.zkz.dreamer.controller;


import com.zkz.dreamer.entity.User;
import com.zkz.dreamer.response.ResponseData;
import com.zkz.dreamer.security.LoginContext;
import com.zkz.dreamer.security.SystemUser;
import com.zkz.dreamer.security.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private LoginContext<User> loginContext;
    @Resource
    private UserInfoService<User> userInfoService;
    @GetMapping("/test/login")
    public ResponseData login(){
        User systemUser = userInfoService.loadUserByUsername("123");
        String s = loginContext.doLogin(systemUser);
        return ResponseData.success(s);
    }
    @PreAuthorize("hasAuthority('123')")
    @GetMapping("/test/index")
    public ResponseData index(){
        SystemUser loginUser = loginContext.getLoginUser();
        return ResponseData.success(loginUser);
    }
}
