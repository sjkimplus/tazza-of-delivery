package com.sparta.tazzaofdelivery.domain.order.dto.request;

import lombok.Getter;

@Getter
public class CartCreateRequest {
    private Long munuId;
    private Long count;
}
