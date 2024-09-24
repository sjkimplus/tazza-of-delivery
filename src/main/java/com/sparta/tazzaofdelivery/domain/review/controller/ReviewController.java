package com.sparta.tazzaofdelivery.domain.review.controller;

import com.sparta.tazzaofdelivery.domain.review.dto.request.CommentCreateRequest;
import com.sparta.tazzaofdelivery.domain.review.dto.request.ReviewCreateRequest;
import com.sparta.tazzaofdelivery.domain.review.dto.response.CommentResponse;
import com.sparta.tazzaofdelivery.domain.review.dto.response.ReviewResponse;
import com.sparta.tazzaofdelivery.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성 API
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody ReviewCreateRequest reviewDto) {
        reviewService.createReview(reviewDto);
        return ResponseEntity.ok().build();
    }

    // 가게별 리뷰 조회 API
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByStore(@PathVariable Long storeId) {
        List<ReviewResponse> reviews = reviewService.getReviewsByStore(storeId);
        return ResponseEntity.ok(reviews);
    }

    // 댓글 생성 API (사장님 댓글 포함)
    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable Long reviewId, @RequestBody CommentCreateRequest commentDto) {
        reviewService.createComment(reviewId, commentDto);
        return ResponseEntity.ok().build();
    }

    // 사장님 댓글 생성 API
    @PostMapping("/{reviewId}/owner-comment")
    public ResponseEntity<Void> createOwnerComment(@PathVariable Long reviewId, @RequestBody CommentCreateRequest commentDto) {
        reviewService.createOwnerComment(reviewId, commentDto); // 사장님 댓글 처리 로직
        return ResponseEntity.ok().build();
    }

    // 리뷰별 댓글 조회 API
    @GetMapping("/{reviewId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByReview(@PathVariable Long reviewId) {
        List<CommentResponse> comments = reviewService.getCommentsByReview(reviewId);
        return ResponseEntity.ok(comments);
    }
}