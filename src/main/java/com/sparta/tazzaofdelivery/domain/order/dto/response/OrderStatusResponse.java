package com.sparta.tazzaofdelivery.domain.order.dto.response;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderStatusResponse {

    private final Long orderId;
    private final OrderStatus orderStatus;
    private final Long storeId;

    @Builder
    public OrderStatusResponse(
            Long orderId,
            OrderStatus orderStatus,
            Long storeId) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.storeId = storeId;
    }

}
