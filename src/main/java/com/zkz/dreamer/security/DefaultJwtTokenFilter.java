package com.zkz.dreamer.security;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zkz.dreamer.cache.ResourceCache;
import com.zkz.dreamer.exception.BaseException;
import com.zkz.dreamer.exception.NotFoundException;
import com.zkz.dreamer.exception.TokenException;
import com.zkz.dreamer.jwt.JwtUtils;
import com.zkz.dreamer.response.ResponseData;
import com.zkz.dreamer.cache.TokenCacheManage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class DefaultJwtTokenFilter extends  AbstractJwtTokenFilter {
    @Resource
    private SecurityConfig securityConfig;
    @Resource
    private TokenCacheManage tokenCacheManage;
    @Resource
    private LoginContext<SystemUser> loginContext;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            doFilter(request, response, filterChain);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> 服务器运行异常,具体信息为：{}",  e.getMessage());
            if (e instanceof BaseException){
                BaseException baseException=(BaseException) e;
                ResponseData.responseExceptionError(response, baseException);
            }else {
                ResponseData.responseExceptionError(response, new BaseException(500,e.getMessage()));
            }
        }
    }
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String tokenStr = request.getHeader(securityConfig.getTokenHeader());
        if (StrUtil.isNotEmpty(tokenStr)) {
            SystemUser systemUser = JwtUtils.verify(tokenStr);
            if (ObjectUtil.isNotNull(systemUser)){
                tokenCacheManage.check(systemUser);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                systemUser.getUser(),
                                null,
                                systemUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
