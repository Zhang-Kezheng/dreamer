package com.zkz.dreamer.jwt;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zkz.dreamer.cache.TokenCacheManage;
import com.zkz.dreamer.exception.DreamerException;
import com.zkz.dreamer.exception.TokenException;
import com.zkz.dreamer.security.SecurityConfig;
import com.zkz.dreamer.security.SystemUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.zkz.dreamer.security.Token;
import com.zkz.dreamer.security.UserInfoService;
import com.zkz.dreamer.util.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JwtUtils {
    private static SecurityConfig securityConfig;
    private static TokenCacheManage tokenCacheManage;
    @Autowired
    public void setSecurityConfig(SecurityConfig securityConfig,TokenCacheManage tokenCacheManage) {
        JwtUtils.securityConfig = securityConfig;
        JwtUtils.tokenCacheManage=tokenCacheManage;
    }
    public static String create(SystemUser systemUser) {
        if (systemUser == null) {
            return null;
        } else {
            Date nowTime = new Date();
            Map<String, Object> header = new HashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");
            Token token=new Token();
            token.setId(systemUser.getId());
            Builder builder = JWT.create().withHeader(header).withClaim("data", GsonUtils.toJson(token)).withIssuedAt(nowTime);
            if (securityConfig.getSurvivalMinutes()>0){
                Date expiresTime = DateUtil.offset(new Date(), DateField.MINUTE,securityConfig.getSurvivalMinutes());
                builder.withExpiresAt(expiresTime);
            }else {
                throw new DreamerException("过期时间应当大于0");
            }
            return builder.sign(getAlgorithm());
        }
    }

   public static  SystemUser verify(String jwtStr) throws TokenException {
        if (!StringUtils.isBlank(jwtStr)) {
            try {
                String jsonToken = JWT.require(getAlgorithm()).build().verify(jwtStr).getClaim("data").as(String.class);
                Token token = GsonUtils.fromJson(jsonToken, Token.class);
                return tokenCacheManage.get(token.getId());
            } catch (TokenExpiredException tokenExpiredException) {
                throw new TokenException("token过期");
            } catch (JWTVerificationException jwtVerificationException) {
                throw new TokenException("token验证失败");
            } catch (Exception e) {
                throw new TokenException("token异常");
            }
        }
        return null;
    }

    private static Algorithm getAlgorithm() throws TokenException {
        String secretKey = securityConfig.getSecretKey();
        if (StringUtils.isBlank(secretKey)) {
            throw new TokenException("密钥为空");
        } else {
            return Algorithm.HMAC256(secretKey);
        }
    }
}
