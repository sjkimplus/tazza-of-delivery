package com.sparta.tazzaofdelivery.domain.review.dto.request;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private Long reviewId;
    private Long userId;
    private String content;

    public CommentCreateRequest(Long reviewId, Long userId, String content) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.content = content;
    }
}
