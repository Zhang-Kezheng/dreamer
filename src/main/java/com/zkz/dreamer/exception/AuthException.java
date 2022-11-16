package com.zkz.dreamer.exception;

public class AuthException extends BaseException{
    public AuthException(String message) {
        super(503,message);
    }
}
