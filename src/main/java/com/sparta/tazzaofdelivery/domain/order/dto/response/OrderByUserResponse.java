package com.sparta.tazzaofdelivery.domain.order.dto.response;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatus;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderByUserResponse {

    private final String menuName;
    private final Double menuPrice;
    private final Long menuCount;
    private final LocalDateTime orderCreatedAt;
    private final Double totalPrice;
    private final String storeName;
    private final OrderStatus orderStatus;

    @Builder
    public OrderByUserResponse(
            String menuName,
            Double menuPrice,
            Long menuCount,
            LocalDateTime orderCreatedAt,
            Double totalPrice,
            Store store,
            OrderStatus orderStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuCount = menuCount;
        this.orderCreatedAt = orderCreatedAt;
        this.totalPrice = totalPrice;
        this.storeName = store.getStoreName();
        this.orderStatus = orderStatus;
    }

}
