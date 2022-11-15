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
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class DefaultJwtTokenFilter extends OncePerRequestFilter implements AbstractJwtTokenFilter {
    @Resource
    private SecurityConfig securityConfig;
    @Resource
    private TokenCacheManage tokenCacheManage;
    @Resource
    private LoginContext<SystemUser> loginContext;
    @Resource
    private ResourceCache resourceCache;
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
                ResponseData.responseExceptionError(response, new BaseException(500,e.getStackTrace()[0].toString()));
            }
        }
    }
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 1.如果当前请求带了token，判断token时效性，并获取当前登录用户信息
        if (!matherPath(resourceCache.getAllResources().toArray(new String[0]),request.getRequestURI())){
            throw new NotFoundException("接口不存在，请检查url是否正确");
        }
        SystemUser systemUser = null;
        if (matherPath(securityConfig.getNoneSecurityUrlPatterns(),request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String tokenStr = request.getHeader(securityConfig.getTokenHeader());
        if (StrUtil.isNotEmpty(tokenStr)) {
            systemUser = JwtUtils.verify(tokenStr);
        }else {
            throw new TokenException("请携带token访问本接口");
        }
        if (ObjectUtil.isNotNull(systemUser)){
            tokenCacheManage.checkToken(systemUser);
            // 3.其他情况放开过滤
            loginContext.put(systemUser);
            filterChain.doFilter(request, response);
        }else {
            throw new TokenException("token已过期");
        }

    }
    private boolean matherPath(String[] urls,String path){
        if (StringUtils.isBlank(path) || urls==null||urls.length==0) {
            return false;
        }
        for (String url : urls) {
            if (isMatch(url, path)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isMatch(String url, String urlPath) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(url, urlPath);
    }
}
