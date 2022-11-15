package com.zkz.dreamer.cache;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class ResourceCache {
    private final Set<String> resourceCaches = CollectionUtil.newHashSet();

    /**
     * 获取所有缓存资源
     *
     * @author yubaoshan
     * @date 2020/7/9 13:52
     */
    public Set<String> getAllResources() {
        return resourceCaches;
    }

    /**
     * 直接缓存所有资源
     *
     * @author yubaoshan
     * @date 2020/7/9 13:52
     */
    public void putAllResources(Set<String> resources) {
        resourceCaches.addAll(resources);
    }
}
