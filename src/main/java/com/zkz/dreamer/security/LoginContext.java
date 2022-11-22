package com.zkz.dreamer.security;

import cn.hutool.core.util.ObjectUtil;
import com.zkz.dreamer.exception.AuthException;
import com.zkz.dreamer.exception.TokenException;
import com.zkz.dreamer.jwt.JwtUtils;
import com.zkz.dreamer.cache.TokenCacheManage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class LoginContext<T> {
    @Resource
    private TokenCacheManage tokenCacheManage;
    public T getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtil.isEmpty(authentication) || authentication.getPrincipal() instanceof String) {
            throw new AuthException("用户未登录");
        } else {
            return (T) authentication.getPrincipal();
        }
    }

    public String doLogin(T user, Collection<? extends GrantedAuthority> authorities){
        SystemUser systemUser=new SystemUser();
        systemUser.setUser(user);
        systemUser.setAuthorities(authorities);
        String s = JwtUtils.create(systemUser);
        tokenCacheManage.put(systemUser);
        return s;
    }
}
