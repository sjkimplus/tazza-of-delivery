package com.sparta.tazzaofdelivery.domain.review.entity;

import com.sparta.tazzaofdelivery.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 댓글 내용
    private String content;

    // 사장님 댓글 여부
    private boolean isOwnerComment;

    // 생성자 (일반 댓글)
    public Comment(Review review, User user, String content) {
        this.review = review;
        this.user = user;
        this.content = content;
        this.isOwnerComment = false;  // 일반 댓글은 사장님 댓글이 아님
    }

    // 생성자 (사장님 댓글)
    public Comment(Review review, User user, String content, boolean isOwnerComment) {
        this.review = review;
        this.user = user;
        this.content = content;
        this.isOwnerComment = isOwnerComment;  // 사장님 댓글 여부 설정
    }
}