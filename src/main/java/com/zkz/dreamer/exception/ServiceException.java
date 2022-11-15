package com.zkz.dreamer.exception;

public class ServiceException extends BaseException{
    public ServiceException(String message) {
        super(500,message);
    }
}
