package com.sparta.tazzaofdelivery.domain.review.service;


import com.sparta.tazzaofdelivery.domain.order.entity.Order;
import com.sparta.tazzaofdelivery.domain.order.repository.OrderRepository;
import com.sparta.tazzaofdelivery.domain.review.dto.request.CommentCreateRequest;
import com.sparta.tazzaofdelivery.domain.review.dto.request.ReviewCreateRequest;
import com.sparta.tazzaofdelivery.domain.review.dto.response.CommentResponse;
import com.sparta.tazzaofdelivery.domain.review.dto.response.ReviewResponse;
import com.sparta.tazzaofdelivery.domain.review.entity.Comment;
import com.sparta.tazzaofdelivery.domain.review.entity.Review;
import com.sparta.tazzaofdelivery.domain.review.repository.CommentRepository;
import com.sparta.tazzaofdelivery.domain.review.repository.ReviewRepository;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.store.repository.StoreRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    // 리뷰 생성
    public void createReview(ReviewCreateRequest reviewDto) {
        Store store = storeRepository.findById(reviewDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Order order = orderRepository.findById(reviewDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("ID가 " + reviewDto.getOrderId() + "인 주문을 찾을 수 없습니다."));

        Review review = new Review(
                store, user, order, reviewDto.getRating(), reviewDto.getContent(), LocalDateTime.now()
        );
        reviewRepository.save(review);
    }

     //가게별 리뷰 조회(최신순)
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByStore(Long storeId) {
        return reviewRepository.findByStore_StoreIdOrderByCreatedAtDesc(storeId)
                .stream()
                .map(review -> new ReviewResponse(review.getId(), review.getContent(), review.getRating()))
                .toList();
    }

    // 댓글 생성
    public void createComment(Long reviewId, CommentCreateRequest commentDto) {
        if (reviewId == null) {
            throw new IllegalArgumentException("리뷰 ID는 null이 될 수 없습니다.");
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Comment comment = new Comment(review, user, commentDto.getContent());
        commentRepository.save(comment);
    }

    // 리뷰별 댓글 조회 (사장님 댓글 포함)
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByReview(Long reviewId) {
        return commentRepository.findByReviewId(reviewId)
                .stream()
                .map(comment -> new CommentResponse(comment.getId(), comment.getContent(), comment.isOwnerComment()))
                .toList();
    }

    // 사장님 댓글 생성 (사장님만 가능)
    public void createOwnerComment(Long reviewId, CommentCreateRequest commentDto) {
        if (reviewId == null) {
            throw new IllegalArgumentException("리뷰 ID는 null이 될 수 없습니다.");
        }

        // reviewId를 사용하여 리뷰를 찾음
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("해당 리뷰를 찾을 수 없습니다."));

        // userId를 사용하여 유저(사장님)를 찾음
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        // 사장님 검증 로직
        Store store = review.getStore();
        if (!store.getUser().equals(user)) {
            throw new RuntimeException("사장님만 댓글을 작성할 수 있습니다.");
        }

        // 댓글 작성 로직
        Comment comment = new Comment(review, user, commentDto.getContent(), true); // 사장님 댓글 여부 설정
        commentRepository.save(comment);
    }
}
