package com.zkz.dreamer.exception;

import com.zkz.dreamer.response.ResponseData;
import com.zkz.dreamer.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Resource
    private ApplicationContext applicationContext;
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData exception(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Exception exception) {
        exception.printStackTrace();
        return ResponseData.failed(exception.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData serviceException(HttpServletRequest request,
                                      HttpServletResponse response,
                                         BaseException serviceException) {
        serviceException.printStackTrace();
        return ResponseData.failed(serviceException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData validatedException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         MethodArgumentNotValidException methodArgumentNotValidException) {
        methodArgumentNotValidException.printStackTrace();
        return ResponseData.failed(methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseData accessDeniedException(HttpServletRequest request,
                                      HttpServletResponse response,AccessDeniedException e)  {
        return ResponseData.failed(new AuthException("权限不足，无法访问"));
    }
}
