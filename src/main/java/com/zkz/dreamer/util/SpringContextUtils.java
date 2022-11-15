package com.zkz.dreamer.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

public class SpringContextUtils {
    public static AnnotationAttributes getAnnotationAttributes(AnnotationMetadata annotationMetadata, Class<?> aClass) {
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(aClass.getName());
        return annotationAttributes == null ? null : AnnotationAttributes.fromMap(annotationAttributes);
    }

    public static void register(BeanDefinitionRegistry beanDefinitionRegistry, Class<?>... someClass) {

        for (Class<?> aClass : someClass) {
            GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
            genericBeanDefinition.setBeanClass(aClass);
            genericBeanDefinition.setSynthetic(true);
            String beanName = (new AnnotationBeanNameGenerator()).generateBeanName(genericBeanDefinition, beanDefinitionRegistry);
            beanDefinitionRegistry.registerBeanDefinition(beanName, genericBeanDefinition);
        }

    }
}
