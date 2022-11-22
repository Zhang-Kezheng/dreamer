package com.zkz.dreamer.security;

import com.zkz.dreamer.EnableDreamer;
import com.zkz.dreamer.exception.GlobalExceptionRegister;
import com.zkz.dreamer.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.annotation.Resource;

@Slf4j
public class WebSecurityRegister implements ImportBeanDefinitionRegistrar {
    @Resource
    private ApplicationContext applicationContext;
    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata, @NotNull BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = SpringContextUtils.getAnnotationAttributes(importingClassMetadata, EnableDreamer.class);
        if (attributes != null) {
            boolean enableWebSecurity = attributes.getBoolean("enableWebSecurity");
            if (enableWebSecurity) {
                log.info("启用接口安全校验");
                boolean enableGlobalMethodSecurity = attributes.getBoolean("enableGlobalMethodSecurity");
                if (enableGlobalMethodSecurity){
                    log.info("启用全局方法安全校验");
                    SpringContextUtils.register(registry, WebSecurityRegister.EnableGlobalMethodSecurityImport.class);
                }
                SpringContextUtils.register(registry, WebSecurityRegister.Importer.class);
            }

        }
    }
    @ComponentScan({"com.zkz.dreamer.security"})
    public static class Importer {
    }
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public static class EnableGlobalMethodSecurityImport {
    }
}
