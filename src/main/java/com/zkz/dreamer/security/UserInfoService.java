package com.zkz.dreamer.security;

public interface UserInfoService<T extends SystemUser> {
    T loadUserByUsername(String username);
}
