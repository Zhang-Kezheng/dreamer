package com.zkz.dreamer.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * redis序列化器
 *
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    private final Class<T> clazz;


    /**
     * 构造函数
     *
     * @author xuyuxiang
     * @date 2020/4/8 19:12
     */
    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 序列化
     *
     * @author xuyuxiang
     * @date 2020/4/8 19:12
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (ObjectUtil.isEmpty(t)) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(Charset.defaultCharset());
    }

    /**
     * 反序列化
     *
     * @author xuyuxiang
     * @date 2020/4/8 19:12
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ObjectUtil.isEmpty(bytes)) {
            return null;
        }
        String str = new String(bytes, Charset.defaultCharset());
        return (T) JSON.parseObject(str, clazz);
    }

}
