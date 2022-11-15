package com.zkz.dreamer.cache;

import com.zkz.dreamer.security.SystemUser;
import com.zkz.dreamer.security.Token;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty(prefix = "dreamer.security",value = "tokenCacheMode",havingValue = "MEMORY",matchIfMissing = true)
@Component
public class MemoryTokenCache implements AbstractTokenCache {
    private final Map<String, SystemUser> systemUserMap=new HashMap<>();
    @Override
    public void put(SystemUser systemUser) {
        systemUserMap.put(systemUser.getId(),systemUser);
    }

    @Override
    public SystemUser get(String id) {
        return systemUserMap.get(id);
    }

    @Override
    public boolean containToken(SystemUser systemUser) {
        return systemUserMap.containsKey(systemUser.getId());
    }
}
