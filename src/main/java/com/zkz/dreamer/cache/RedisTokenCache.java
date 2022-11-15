package com.zkz.dreamer.cache;

import com.zkz.dreamer.security.SystemUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "dreamer.security",value = "tokenCacheMode",havingValue = "REDIS")
public class RedisTokenCache implements AbstractTokenCache {
    @Override
    public void put(SystemUser systemUser) {

    }

    @Override
    public SystemUser get(String id) {
        return null;
    }

    @Override
    public boolean containToken(SystemUser systemUser) {
        return false;
    }
}
