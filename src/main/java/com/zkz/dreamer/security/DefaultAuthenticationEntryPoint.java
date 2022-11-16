package com.zkz.dreamer.security;

import com.zkz.dreamer.cache.ResourceCache;
import com.zkz.dreamer.exception.AuthException;
import com.zkz.dreamer.exception.NotFoundException;
import com.zkz.dreamer.util.PathMatherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Slf4j
public class DefaultAuthenticationEntryPoint  implements AuthenticationEntryPoint , Serializable {
    @Resource
    private ResourceCache resourceCache;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        if (!PathMatherUtils.matherPath(resourceCache.getAllResources().toArray(new String[0]),requestUri)){
            throw new NotFoundException("接口不存在，请检查url是否正确");
        }
        throw new AuthException("未登录，请先登录");
    }
}
