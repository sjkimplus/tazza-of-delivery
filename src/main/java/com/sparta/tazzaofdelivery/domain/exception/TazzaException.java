package com.sparta.tazzaofdelivery.domain.exception;

import lombok.Getter;

@Getter
public class TazzaException extends RuntimeException {
    private ErrorCode errorCode;

    public TazzaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
