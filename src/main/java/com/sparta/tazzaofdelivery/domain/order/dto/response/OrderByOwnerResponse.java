package com.sparta.tazzaofdelivery.domain.order.dto.response;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatus;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderByOwnerResponse {
    private final String menuName;
    private final Double menuPrice;
    private final Long menuCount;
    private final LocalDateTime orderCreatedAt;
    private final Double totalPrice;
    private final OrderStatus orderStatus;
    private final String userName;

    @Builder
    public OrderByOwnerResponse(
            String menuName,
            Double menuPrice,
            Long menuCount,
            LocalDateTime orderCreatedAt,
            Double totalPrice,
            OrderStatus orderStatus,
            User user) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuCount = menuCount;
        this.orderCreatedAt = orderCreatedAt;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.userName = user.getName();
    }
}
