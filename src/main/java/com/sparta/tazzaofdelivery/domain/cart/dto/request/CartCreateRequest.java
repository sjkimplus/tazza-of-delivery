package com.sparta.tazzaofdelivery.domain.cart.dto.request;

import lombok.Getter;

@Getter
public class CartCreateRequest {
    private Long menuId;
    private Long count;
}
