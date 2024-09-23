package com.sparta.tazzaofdelivery.domain.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cart")
@NoArgsConstructor
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
    @Column(name = "menu", nullable = false)
    private Long menuId;
//    @Reference
//    private Set<MenuOrder> menuOrder = new HashSet<>();

    @Column(name = "menu_count", nullable = false)
    private Long menuCount;

    // created_at : 장바구니에 생성 시간
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime cartCreatedAt;

    // cart_status : 장바구니 상태
    @Column(name = "cart_status", nullable = false)
    @Convert(converter = CartStatusConverter.class)
    private CartStatus cartStatus;


    public Cart(
            Long storeId,
            Long userId,
            Long menuId,
            Long menuCount,
            LocalDateTime now,
            CartStatus cartStatus) {
        this.storeId = storeId;
        this.userId = userId;
        this.menuId = menuId;
        this.menuCount = menuCount;
        this.cartStatus = cartStatus;
        this.cartCreatedAt = now;

    }

    public void update(
            Long menuId,
            Long count) {
        this.menuId = menuId;
        this.menuCount = count;
    }

    public void updateStatus(CartStatus cartStatus) {
        this.cartStatus=cartStatus;
    }
}
