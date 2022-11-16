package com.zkz.dreamer.cache;

import com.zkz.dreamer.security.SystemUser;

import java.util.concurrent.TimeUnit;

public interface  AbstractTokenCache {
    void put(SystemUser systemUser, long timeout, TimeUnit timeUnit);
    SystemUser get(String id);
    boolean containToken(SystemUser systemUser);
}
