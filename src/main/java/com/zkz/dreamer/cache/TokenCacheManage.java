package com.zkz.dreamer.cache;

import com.zkz.dreamer.exception.TokenException;
import com.zkz.dreamer.security.SystemUser;
import com.zkz.dreamer.security.Token;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TokenCacheManage {
    @Resource
    private AbstractTokenCache abstractTokenCache;

    public void putToken(SystemUser systemUser){
        abstractTokenCache.put(systemUser);
    }

    public SystemUser get(String id){
        return abstractTokenCache.get(id);
    }


    public void checkToken(SystemUser systemUser){
        if (!abstractTokenCache.containToken(systemUser)){
            throw new TokenException("token已过期，请重新登录");
        }
    }
}
