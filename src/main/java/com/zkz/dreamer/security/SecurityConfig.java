package com.zkz.dreamer.security;

import com.zkz.dreamer.cache.TokenCacheMode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Data
@ConfigurationProperties("dreamer.security")
public class SecurityConfig {
    /**
     * 放开权限校验的接口
     */
    private String[] noneSecurityUrlPatterns={
            "/**"
    };
    private TokenCacheMode tokenCacheMode=TokenCacheMode.MEMORY;

    private String tokenHeader = "access-token";

    private int timeout=60;

    private TimeUnit timeUnit=TimeUnit.MINUTES;

    private String secretKey;
}
