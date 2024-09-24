package com.sparta.tazzaofdelivery.domain.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewCreateRequest {
    private Long storeId;
    private Long userId;
    private Long orderId;
    private int rating;
    private String content;

    public ReviewCreateRequest(Long storeId, Long orderId, Long userId, int rating, String content) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
    }
}
