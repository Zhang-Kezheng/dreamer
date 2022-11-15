package com.zkz.dreamer.exception;

import com.zkz.dreamer.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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

}
