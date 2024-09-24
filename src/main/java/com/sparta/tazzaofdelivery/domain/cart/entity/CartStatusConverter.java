package com.sparta.tazzaofdelivery.domain.cart.entity;

import jakarta.persistence.AttributeConverter;

public class CartStatusConverter implements AttributeConverter<CartStatus, Integer> {
    // enum상태를 DB에 code 값으로 넣을 거라고 정의
    public Integer convertToDatabaseColumn(CartStatus cartStatus) {
        return cartStatus.getCartCode();
    }

    // DB에서 읽어온 값을 사용할 값(CartStatus)으로 변환
    public CartStatus convertToEntityAttribute(Integer dbData) {
        return CartStatus.ofCode(dbData);
    }
}
