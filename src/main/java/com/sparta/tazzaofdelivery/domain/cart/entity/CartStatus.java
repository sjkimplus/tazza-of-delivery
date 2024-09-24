package com.sparta.tazzaofdelivery.domain.cart.entity;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CartStatus {
    VALID(0,"유요한"),
    ORDERED(1,"주문된");

    private final Integer cartCode;
    private final String cartDesc;

    CartStatus(Integer cartCode, String cartDesc) {
        this.cartCode = cartCode;
        this.cartDesc = cartDesc;
    }

    public static CartStatus ofCode(Integer dbData) {
        return Arrays.stream(CartStatus.values())
                .filter(c->c.getCartCode().equals(dbData))
                .findAny()
                .orElseThrow(()->new TazzaException(ErrorCode.CART_STATUS_CODE_NOT_FOUND));
    }
}
