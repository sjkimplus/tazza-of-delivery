package com.sparta.tazzaofdelivery.domain.cart.dto.response;

import com.sparta.tazzaofdelivery.domain.cart.entity.CartStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CartCreateResponse {
    private final Long cartId;
    private final Long storeId;
    private final Long userId;
    private final Long menuId;
    private final Long menuCound;
    private final LocalDateTime cartCreatedAt;
    private final CartStatus cartStatus;

    @Builder
    public CartCreateResponse(
            Long cartId,
            Long storeId,
            Long userId,
            Long menuId,
            Long menuCount,
            LocalDateTime cartCreatedAt,
            CartStatus cartStatus) {
        this.cartId = cartId;
        this.storeId = storeId;
        this.userId = userId;
        this.menuId = menuId;
        this.menuCound = menuCount;
        this.cartCreatedAt = cartCreatedAt;
        this.cartStatus = cartStatus;
    }
}
