package com.sparta.tazzaofdelivery.domain.order.entity;

import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatus;
import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private Timestamp orderCreated;

    // order_status : 주문 상태
    @Column(name="order_status", nullable = false)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    @Column(name="is_review", nullable = false)
    private Boolean isReview;

    @Column(name="menu", nullable = false, length = 500)
    private String menu;




}
