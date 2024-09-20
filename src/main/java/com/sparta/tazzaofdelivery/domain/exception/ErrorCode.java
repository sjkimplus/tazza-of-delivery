package com.sparta.tazzaofdelivery.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Token ErrorCode


    // User ErrorCode


    // Menu ErrorCode


    // Order ErrorCode


    // Review Errorcode


    // Store Errorcode




    // 아래 코드 위에 ErrorCode 작성해 주세요!
    NOT_FOUND(HttpStatus.NOT_FOUND, "찾지못했습니다.");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.status = httpStatus;
        this.message = message;
    }
}
