package com.sparta.tazzaofdelivery.domain.review.entity;

import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 별점 (1~5점)
    private int rating;

    // 리뷰 내용
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 생성자 (필수 필드 초기화)
    public Review(Store store, User user, Order order, int rating, String content, LocalDateTime createdAt) {
        this.store = store;
        this.user = user;
        this.order = order;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }
}
