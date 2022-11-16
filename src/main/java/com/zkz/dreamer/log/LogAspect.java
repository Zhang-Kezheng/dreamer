package com.zkz.dreamer.log;

import com.zkz.dreamer.exception.BaseException;
import com.zkz.dreamer.exception.GlobalExceptionHandler;
import com.zkz.dreamer.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@Aspect
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getPoint() {
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postPoint() {
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deletePoint() {
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putPoint() {
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestPoint() {
    }
    @Around("getPoint()||postPoint()||deletePoint()||putPoint()||requestPoint()")
    public Object timeCount(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object result=pjp.proceed();
        log.info("{} {} {}; process: {}ms;  params: {};  result: {}",request.getMethod(), request.getRequestURL(), RequestUtils.getIp(request), System.currentTimeMillis() - time,pjp.getArgs(),result.toString());
        return result;
    }

    @AfterThrowing(
            throwing = "ex",
            pointcut = "getPoint()||postPoint()||deletePoint()||putPoint()||requestPoint()"
    )
    public void doThrowing(Throwable ex) {
        if (ex instanceof BaseException) {
            BaseException exception=(BaseException) ex;
            log.info("{} {} {}; errorCode:{} errorMsg:{} rawMsg:{}",request.getMethod(), request.getRequestURL(), RequestUtils.getIp(request), exception.getCode(),exception.getMessage(),ex.getMessage());
        } else {
            log.error(ex.getMessage());
        }

    }
}
