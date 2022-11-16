package com.zkz.dreamer.security;

import com.zkz.dreamer.exception.AuthException;
import com.zkz.dreamer.util.SpringContextUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration    {
    @Resource
    private SecurityConfig securityConfig;
    @Resource
    private ApplicationContext applicationContext;
    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //开启跨域访问
        http.cors();
        http.logout().disable();
        BeanDefinitionRegistry beanDefinitionRegistry=(BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        AuthenticationEntryPoint authenticationEntryPoint = applicationContext.getBeansOfType(AuthenticationEntryPoint.class).values().stream().filter(Objects::nonNull).findFirst().orElseGet(() -> {
            SpringContextUtils.register(beanDefinitionRegistry,DefaultAuthenticationEntryPoint.class);
            return applicationContext.getBean(DefaultAuthenticationEntryPoint.class);
        });
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        for (String notAuthResource : securityConfig.getNoneSecurityUrlPatterns()) {
            http.authorizeRequests().antMatchers(notAuthResource).permitAll();
        }
        //其余的都需授权访问
        http.authorizeRequests().anyRequest().authenticated();
        //前置token过滤器
        AbstractJwtTokenFilter abstractJwtTokenFilter = applicationContext.getBeansOfType(AbstractJwtTokenFilter.class).values().stream().filter(Objects::nonNull).findFirst().orElseGet(() -> {
            SpringContextUtils.register(beanDefinitionRegistry,DefaultJwtTokenFilter.class);
            return applicationContext.getBean(DefaultJwtTokenFilter.class);
        });
        http.addFilterBefore(abstractJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //用户详情service
        AbstractUserDetailService abstractUserDetailService = applicationContext.getBeansOfType(AbstractUserDetailService.class).values().stream().filter(Objects::nonNull).findFirst().orElseGet(() -> {
            SpringContextUtils.register(beanDefinitionRegistry,DefaultUserDetailsService.class);
            return applicationContext.getBean(DefaultUserDetailsService.class);
        });
        http.userDetailsService(abstractUserDetailService);

        //全局不创建session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //禁用页面缓存，返回的都是json
        http.headers()
                .frameOptions().disable()
                .cacheControl();
        return http.build();
    }

}
