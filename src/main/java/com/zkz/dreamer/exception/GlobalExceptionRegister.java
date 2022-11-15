package com.zkz.dreamer.exception;

import com.zkz.dreamer.EnableDreamer;
import com.zkz.dreamer.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

@Slf4j
public class GlobalExceptionRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata, @NotNull BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = SpringContextUtils.getAnnotationAttributes(importingClassMetadata, EnableDreamer.class);
        if (attributes != null) {
            boolean enableWebSecurity = attributes.getBoolean("enableGlobalExceptionHandler");
            if (enableWebSecurity) {
                log.info("启用接口统一异常拦截");
                SpringContextUtils.register(registry, GlobalExceptionRegister.Importer.class);
            }

        }
    }
    @ComponentScan({"com.zkz.dreamer.exception"})
    public static class Importer {
        public Importer() {
        }
    }
}
