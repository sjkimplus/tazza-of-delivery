package com.sparta.tazzaofdelivery.domain.order.dto.response;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.CartStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CartCreateResponse {
    private Long cartId;
    private Long storeId;
    private Long userId;
    private String menuOrder;
    private LocalDateTime cartCreatedAt;
    private CartStatus cartStatus;


    public CartCreateResponse(
            Long cartId,
            Long storeId,
            Long userId,
            String menuOrder,
            LocalDateTime cartCreatedAt,
            CartStatus cartStatus) {
        this.cartId = cartId;
        this.storeId = storeId;
        this.userId = userId;
        this.menuOrder = menuOrder;
        this.cartCreatedAt = cartCreatedAt;
        this.cartStatus = cartStatus;

    }
}
