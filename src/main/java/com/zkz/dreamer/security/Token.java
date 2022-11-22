package com.zkz.dreamer.security;

import lombok.Data;

@Data
public class Token {
    private String id;
    private long authTime;
    private long expireTime;
}
