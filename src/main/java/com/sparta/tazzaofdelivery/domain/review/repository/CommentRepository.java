package com.sparta.tazzaofdelivery.domain.review.repository;

import com.sparta.tazzaofdelivery.domain.review.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewId(Long reviewId); // 리뷰별 댓글 조회
}
