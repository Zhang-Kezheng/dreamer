package com.zkz.dreamer.security;

import cn.hutool.core.util.ObjectUtil;
import com.zkz.dreamer.exception.TokenException;
import com.zkz.dreamer.jwt.JwtUtils;
import com.zkz.dreamer.cache.TokenCacheManage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.security.auth.message.AuthException;

@Component
public class LoginContext<T extends SystemUser> {
    @Resource
    private TokenCacheManage tokenCacheManage;
    public T getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtil.isEmpty(authentication) || authentication.getPrincipal() instanceof String) {
            throw new TokenException("用户没登录");
        } else {
            return (T) authentication.getPrincipal();
        }
    }

    public String doLogin(T user){
        String s = JwtUtils.create(user);
        tokenCacheManage.putToken(user);
        return s;
    }
    public void put(T systemUser){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        systemUser,
                        null,
                        systemUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
