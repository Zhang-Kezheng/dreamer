package com.zkz.dreamer.log;

import com.zkz.dreamer.EnableDreamer;
import com.zkz.dreamer.security.WebSecurityRegister;
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
public class LogAspectRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata, @NotNull BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = SpringContextUtils.getAnnotationAttributes(importingClassMetadata, EnableDreamer.class);
        if (attributes != null) {
            boolean enableWebSecurity = attributes.getBoolean("enableLogAspect");
            if (enableWebSecurity) {
                log.info("启用接口日志记录");
                SpringContextUtils.register(registry, LogAspectRegister.Importer.class);
            }

        }
    }
    @ComponentScan({"com.zkz.dreamer.log"})
    public static class Importer {
        public Importer() {
        }
    }
}
