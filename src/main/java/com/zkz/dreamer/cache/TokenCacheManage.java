package com.zkz.dreamer.cache;

import com.zkz.dreamer.exception.TokenException;
import com.zkz.dreamer.security.SecurityConfig;
import com.zkz.dreamer.security.SystemUser;
import com.zkz.dreamer.security.Token;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class TokenCacheManage {
    @Resource
    private AbstractTokenCache abstractTokenCache;
    @Resource
    private SecurityConfig securityConfig;
    public void put(SystemUser systemUser){
        abstractTokenCache.put(systemUser,securityConfig.getTimeout(),securityConfig.getTimeUnit());
    }

    public SystemUser get(String id){
        return abstractTokenCache.get(id);
    }


    public void check(SystemUser systemUser){
        if (!abstractTokenCache.containToken(systemUser)){
            throw new TokenException("token已过期，请重新登录");
        }
    }
}
