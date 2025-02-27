package com.sparta.tazzaofdelivery.domain.order.entity;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatus;
import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatusConverter;

import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "food_order")
public class Order {

    // order_id : 주문 아이디 (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    // total_price : 주문 금액
    @Column(name="total_price", nullable = false)
    private Double totalPrice;

    // order_created : 주문 시간
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime orderCreatedAt;

    // order_status : 주문 상태
    @Column(name="order_status", nullable = false)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    // is_review : 리뷰 여부
    @Column(name="is_review", nullable = false)
    private Boolean isReview;

//    // menu : 주문 JSON 전체 스냅샷 string
//    @Column(name="menu", nullable = false, length = 500)
//    private String menu;

    @Column(name="menu_name", nullable = false, length = 100)
    private String menuName;

    @Column(name="menu_price", nullable = false)
    private Double menuPrice;

    @Column(name = "menu_count", nullable = false)
    private Long menuCount;

    // user_id : User ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    // store_id : Store ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id", nullable = false)
    private Store store;


    public Order(
            Double totalPrice,
            LocalDateTime now,
            OrderStatus orderStatus,
            boolean b,
            String menuName,
            Double price,
            Long menuCount,
            User orderUser,
            Store orderStore) {
        this.totalPrice = totalPrice;
        this.orderCreatedAt=now;
        this.orderStatus = orderStatus;
        this.isReview = b;
        this.menuName = menuName;
        this.menuPrice=price;
        this.menuCount=menuCount;
        this.user=orderUser;
        this.store=orderStore;
    }

    public void approve(OrderStatus orderStatus) {
        this.orderStatus=orderStatus;
    }
}
