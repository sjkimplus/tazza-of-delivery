package com.sparta.tazzaofdelivery.domain.order.entity;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.CartStatus;
import com.sparta.tazzaofdelivery.domain.order.orderconfig.CartStatusConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Entity
//@NoArgsConstructor

//@Builder
@Getter
@Setter
@RedisHash(value = "cart", timeToLive = 60 * 60 * 24)
public class Cart {

    // cart_id : 장바구니 ID
    @Id
    @jakarta.persistence.Id
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
    private String menuOrder;
//    @Reference
//    private Set<MenuOrder> menuOrder = new HashSet<>();

    // created_at : 장바구니에 생성 시간
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime cartCreatedAt;

    // cart_status : 장바구니 상태
    @Column(name = "cart_status", nullable = false)
    @Convert(converter = CartStatusConverter.class)
    private CartStatus cartStatus;


//    public void addMenuOrder(MenuOrder menuOrder) {
//        this.menuOrder.add(menuOrder);
//    }

    public Cart(
            Long storeId,
            Long userId,
            String menu,
            LocalDateTime now,
            CartStatus cartStatus) {
        this.storeId = storeId;
        this.userId = userId;
        this.menuOrder = menu;
        this.cartCreatedAt = now;
        this.cartStatus = cartStatus;
    }
}
