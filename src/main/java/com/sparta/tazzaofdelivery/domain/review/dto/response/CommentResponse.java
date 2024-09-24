package com.sparta.tazzaofdelivery.domain.review.dto.response;

import lombok.Getter;

@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private boolean isOwnerComment;

    public CommentResponse(Long id, String content, boolean isOwnerComment) {
        this.id = id;
        this.content = content;
        this.isOwnerComment = isOwnerComment;
    }
    // 필요한 경우 추가적인 필드 선언 가능
}
