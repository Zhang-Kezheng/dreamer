package com.zkz.dreamer.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException{
    private int code;
    private String message;

    public BaseException(String message) {
        super(message);
        this.code=500;
        this.message = message;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
