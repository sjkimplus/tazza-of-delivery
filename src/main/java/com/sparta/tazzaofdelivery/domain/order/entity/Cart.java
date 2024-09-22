package com.sparta.tazzaofdelivery.domain.order.entity;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.CartStatus;
import com.sparta.tazzaofdelivery.domain.order.orderconfig.CartStatusConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;


@Getter
@Builder
@RedisHash(value = "cart", timeToLive = 60*60*24)
public class Cart {

    // cart_id : 장바구니 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private Long cartId;

    // store_id : 가게 ID
    @Column(name = "store_id", nullable = false)
    private Long storeId;

    // user_id : 사용자 ID
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // menu : 메뉴 JSON
    @Column(name = "menu", nullable = false, length = 500)
    private String menu;

    // created_at : 장바구니에 생성 시간
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime cartCreated;

    // cart_status : 장바구니 상태
    @Column(name = "cart_status", nullable = false)
    @Convert(converter = CartStatusConverter.class)
    private CartStatus cartStatus;

}
