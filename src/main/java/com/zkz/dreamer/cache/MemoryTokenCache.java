package com.zkz.dreamer.cache;

import com.zkz.dreamer.security.SystemUser;
import com.zkz.dreamer.security.Token;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ConditionalOnProperty(prefix = "dreamer.security",value = "token-cache-mode",havingValue = "memory",matchIfMissing = true)
@Component
public class MemoryTokenCache implements AbstractTokenCache {
    private final MapCacheComponent<String, SystemUser> systemUserMap=new MapCacheComponent<>();
    @Override
    public void put(SystemUser systemUser, long timeout, TimeUnit timeUnit) {
        systemUserMap.put(systemUser.getId(),systemUser);
    }

    @Override
    public SystemUser get(String id) {
        return systemUserMap.get(id);
    }

    @Override
    public boolean containToken(SystemUser systemUser) {
        return systemUserMap.get(systemUser.getId())!=null;
    }
}
