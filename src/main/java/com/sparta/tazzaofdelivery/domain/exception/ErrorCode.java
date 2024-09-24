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
    USER_SAME_PW_ERROR(HttpStatus.NOT_FOUND, "현재와 동일한 비밀번호로 변경할 수 없습니다"),


    // Menu ErrorCode
    MENU_CREATE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "메뉴 등록 및 수정은 가게 사장님만 가능합니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 메뉴입니다."),
    MENU_INVALID_PRICE(HttpStatus.BAD_REQUEST, "가격은 0이상의 양수만 입력가능합니다."),
    MENU_ISDELETED(HttpStatus.BAD_REQUEST, "삭제된 메뉴입니다."),


    // Order ErrorCode
    ORDER_STATUS_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문 상태 코드 입니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문 입니다."),
    ORDER_USER_NOT_EQUAL(HttpStatus.NOT_FOUND, "해당 사용자의 주문이 아닙니다."),
    USER_ORDER_NOT_EXIST(HttpStatus.NOT_FOUND, "사용자의 주문내역이 없습니다."),
    OWNER_ORDER_NOT_EXIST(HttpStatus.NOT_FOUND,"들어온 주문내역이 없습니다."),


    // Cart ErrorCode
    CART_STATUS_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 장바구니 상태 코드 입니다."),
    CART_USER_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 유저의 장바구니가 좋재하지 않습니다."),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 장바구니는 존재하지 않습니다."),
    CART_USER_NOT_EQUAL(HttpStatus.NOT_FOUND,"해당 사용자의 장바구니가 아닙니다."),



    // Review Errorcode


    // Store Errorcode
    STORE_FORBIDDEN(HttpStatus.FORBIDDEN,"사장님 권한을 가진 사용자만 가게를 생성할 수 있습니다."),
    STORE_BAD_REQUEST(HttpStatus.BAD_REQUEST,"사장님은 최대 3개의 가게까지만 운영할 수 있습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 가게를 찾을 수 없습니다."),
    STORE_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN,"해당 가게의 소유자가 아닙니다."),


    // Favorite ErrorCode
    ALREADY_FAVORITE(HttpStatus.BAD_REQUEST,"이미 즐겨찾기한 가게입니다." ),
    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "즐겨찾기한 가게를 찾을 수 없습니다." ),

    // search ErroCode
    KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "검색한 키워드가 없습니다."),

    // 아래 코드 위에 ErrorCode 작성해 주세요!
    NOT_FOUND(HttpStatus.NOT_FOUND, "찾지못했습니다.");



    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.status = httpStatus;
        this.message = message;
    }
}
