package com.sparta.tazzaofdelivery.domain.review.dto.response;

import lombok.Getter;

@Getter
public class ReviewResponse {
    private Long id;
    private String content;
    private int rating;

    public ReviewResponse(Long id, String content, int rating) {
        this.id = id;
        this.content = content;
        this.rating = rating;
    }
    // 필요한 경우 추가적인 필드 선언 가능
}