package com.sparta.tazzaofdelivery.domain.exception;

import lombok.Getter;

@Getter
public class TazzaException extends RuntimeException {
    //  run time exceptions are only detected during the execution of your app
    private ErrorCode errorCode;

    public TazzaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
