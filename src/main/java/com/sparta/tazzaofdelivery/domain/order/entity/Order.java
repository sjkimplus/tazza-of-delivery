package com.sparta.tazzaofdelivery.domain.order.entity;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatus;
import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatusConverter;

import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order")
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
    @Column(name="order_created", nullable = false)
    private LocalDateTime orderCreated;

    // order_status : 주문 상태
    @Column(name="order_status", nullable = false)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    // is_review : 리뷰 여부
    @Column(name="is_review", nullable = false)
    private Boolean isReview;

    // menu : 주문 JSON 전체 스냅샷 string
    @Column(name="menu", nullable = false, length = 500)
    private String menu;

    // user_id : User ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    // store_id : Store ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id", nullable = false)
    private Store store;


}
