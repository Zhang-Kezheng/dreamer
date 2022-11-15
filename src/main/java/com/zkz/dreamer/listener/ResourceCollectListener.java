package com.zkz.dreamer.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.zkz.dreamer.cache.ResourceCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class ResourceCollectListener implements CommandLineRunner {

    @Resource
    private ResourceCache resourceCache;
    @Resource
    private ApplicationContext applicationContext;
    @Override
    public void run(String... args) {
        Set<String> urlSet = CollectionUtil.newHashSet();
        Map<String, RequestMappingHandlerMapping> mappingMap = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);
        Collection<RequestMappingHandlerMapping> mappings = mappingMap.values();
        for (RequestMappingHandlerMapping mapping : mappings) {
            Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
            map.keySet().forEach(requestMappingInfo -> {
                PathPatternsRequestCondition pathPatternsCondition = requestMappingInfo.getPathPatternsCondition();
                if (pathPatternsCondition!=null){
                    Set<String> patterns = pathPatternsCondition.getPatternValues();
                    urlSet.addAll(patterns);
                }
            });
        }

        //2.汇总添加到缓存
        resourceCache.putAllResources(urlSet);

        log.info(">>> 缓存资源URL集合完成!资源数量：{}", urlSet.size());
    }
}
