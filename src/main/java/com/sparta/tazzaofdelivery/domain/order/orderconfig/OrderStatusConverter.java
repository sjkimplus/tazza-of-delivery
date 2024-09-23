package com.sparta.tazzaofdelivery.domain.order.orderconfig;

import jakarta.persistence.AttributeConverter;

public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {
    // enum 상태를 DB에 code 값으로 넣을 거라고 정의
    @Override
    public Integer convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus.getOrderCode();
    }

    // DB에서 읽어온 값을 사용할 값(OrderStatus)으로 변환
    @Override
    public OrderStatus convertToEntityAttribute(Integer dbData) {
        return OrderStatus.ofCode(dbData);
    }


}
