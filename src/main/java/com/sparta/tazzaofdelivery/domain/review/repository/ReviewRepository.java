package com.sparta.tazzaofdelivery.domain.review.repository;

import com.sparta.tazzaofdelivery.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStore_StoreIdOrderByCreatedAtDesc(Long storeId); // 가게별 리뷰 조회(최신순)
}
