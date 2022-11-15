package com.zkz.dreamer.cache;

import com.zkz.dreamer.security.SystemUser;

public interface  AbstractTokenCache {
    void put(SystemUser systemUser);
    SystemUser get(String id);
    boolean containToken(SystemUser systemUser);
}
