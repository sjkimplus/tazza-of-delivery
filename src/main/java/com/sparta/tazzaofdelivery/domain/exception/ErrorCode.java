package com.sparta.tazzaofdelivery.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Token ErrorCode


    // User ErrorCode
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저 입니다."),
    USER_ID_DUPLICATION(HttpStatus.BAD_REQUEST, "중복되는 아이디 입니다."),
    USER_PW_ERROR(HttpStatus.BAD_REQUEST, "비밀 번호가 아이디와 일치하지 않습니다."),


    // Menu ErrorCode


    // Order ErrorCode
    ORDER_STATUS_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문 상태 코드 입니다."),


    // Review Errorcode


    // Store Errorcode
    STORE_FORBIDDEN(HttpStatus.FORBIDDEN,"사장님 권한을 가진 사용자만 가게를 생성할 수 있습니다."),
    STORE_BAD_REQUEST(HttpStatus.BAD_REQUEST,"사장님은 최대 3개의 가게까지만 운영할 수 있습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 가게를 찾을 수 없습니다."),
    STORE_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN,"해당 가게의 소유자가 아닙니다."),



    // 아래 코드 위에 ErrorCode 작성해 주세요!
    NOT_FOUND(HttpStatus.NOT_FOUND, "찾지못했습니다.");



    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.status = httpStatus;
        this.message = message;
    }
}
