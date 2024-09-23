package com.sparta.tazzaofdelivery.domain.order.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash
public class MenuOrder {
    @Id
    private Long menuOrderId;

    // 담은 menu ID
    private Long menuId;

    // 담은 menu 개수
    private Long count;
}
