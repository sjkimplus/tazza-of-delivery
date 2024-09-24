package com.sparta.tazzaofdelivery.domain.order.dto.response;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatus;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderCreateResponse {
    private final Long orderId;
    private final Double totalPrice;
    private final LocalDateTime orderCreatedAt;
    private final OrderStatus orderStatus;
    private final String menuName;
    private final Double menuPrice;
    private final Long menuCount;
    private final String userName;
    private final String storeName;
    private final Long StoreId;
    private final Boolean isReview;

    @Builder
    public OrderCreateResponse(
            Long orderId,
            Double totalPrice,
            LocalDateTime orderCreatedAt,
            OrderStatus orderStatus,
            String menuName,
            Double menuPrice,
            Long menuCount,
            User user,
            Store store,
            Boolean isReview) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderCreatedAt = orderCreatedAt;
        this.orderStatus = orderStatus;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuCount = menuCount;
        this.userName = user.getName();
        this.storeName = store.getStoreName();
        this.StoreId = store.getStoreId();
        this.isReview = isReview;
    }
}
