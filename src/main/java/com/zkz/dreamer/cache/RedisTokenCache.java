package com.zkz.dreamer.cache;

import com.zkz.dreamer.security.SystemUser;
import com.zkz.dreamer.util.FastJson2JsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(prefix = "dreamer.security",value = "token-cache-mode",havingValue = "redis")
public class RedisTokenCache implements AbstractTokenCache {
    private ValueOperations<String,SystemUser> ops;
    @Autowired
    public void setRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, SystemUser> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer<>(SystemUser.class));
        redisTemplate.afterPropertiesSet();
        ops = redisTemplate.opsForValue();
    }
    @Override
    public void put(SystemUser systemUser, long timeout, TimeUnit timeUnit) {
        ops.set(systemUser.getId(),systemUser,timeout,timeUnit);
    }

    @Override
    public SystemUser get(String id) {
        return ops.get(id);
    }

    @Override
    public boolean containToken(SystemUser systemUser) {
        return ops.get(systemUser.getId())!=null;
    }
}
