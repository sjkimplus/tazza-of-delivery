package com.sparta.tazzaofdelivery.domain.review.service;

import com.sparta.tazzaofdelivery.domain.order.entity.Order;
import com.sparta.tazzaofdelivery.domain.order.repository.OrderRepository;
import com.sparta.tazzaofdelivery.domain.review.dto.request.ReviewCreateRequest;
import com.sparta.tazzaofdelivery.domain.review.entity.Review;
import com.sparta.tazzaofdelivery.domain.review.repository.CommentRepository;
import com.sparta.tazzaofdelivery.domain.review.repository.ReviewRepository;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import com.sparta.tazzaofdelivery.domain.store.repository.StoreRepository;
import com.sparta.tazzaofdelivery.domain.store.service.StoreService;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private StoreService storeService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void 리뷰_생성_성공() {
        // given
        Long storeId = 1L;
        Long userId = 2L;
        Long orderId = 3L;
        int rating = 5;
        String content = "좋은 리뷰입니다.";
        LocalTime time = LocalTime.now();  // Capturing the mosaic of time

        ReviewCreateRequest request = new ReviewCreateRequest(storeId, orderId, userId, rating, content);

        User user = new User(userId, UserType.CUSTOMER);
        Store store = new Store("store name", time, time, 10000L, "공지", StoreStatus.ACTIVE, user);
        Order order = new Order();  // The labyrinth of Order creation

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // when
        reviewService.createReview(request);  // Embarking on the review creation journey

        // then
        ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);  // Captivating the Review object
        verify(reviewRepository).save(reviewCaptor.capture());  // Ensuring the save method intertwines as expected

        Review capturedReview = reviewCaptor.getValue();

        // Verifying the fields are as expected in this intricate tapestry
        assertEquals(store, capturedReview.getStore());
        assertEquals(user, capturedReview.getUser());
        assertEquals(order, capturedReview.getOrder());
        assertEquals(rating, capturedReview.getRating());
        assertEquals(content, capturedReview.getContent());
        assertNotNull(capturedReview.getCreatedAt());  // Time should be captured in this verdant crucible of creation
    }

    @Test
    void 리뷰_생성_가게_존재하지_않음() {
        // given
        Long storeId = 1L;
        Long userId = 2L;
        Long orderId = 3L;
        ReviewCreateRequest request = new ReviewCreateRequest(storeId, orderId, userId, 5, "리뷰 내용");

        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(RuntimeException.class, () -> reviewService.createReview(request));
        assertEquals("가게를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    void 리뷰_생성_사용자_존재하지_않음() {
        // given
        Long storeId = 1L;
        Long userId = 2L;
        Long orderId = 3L;
        ReviewCreateRequest request = new ReviewCreateRequest(storeId, orderId, userId, 5, "리뷰 내용");

        Store store = new Store();
        ReflectionTestUtils.setField(store, "storeId", storeId);

        // 가게는 존재하는 것으로 설정
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        // 사용자가 존재하지 않는 상황 설정
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(RuntimeException.class, () -> reviewService.createReview(request));
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    void 리뷰_생성_주문_존재하지_않음() {
        // given
        Long storeId = 1L;
        Long userId = 2L;
        Long orderId = 3L;
        ReviewCreateRequest request = new ReviewCreateRequest(storeId, orderId, userId, 5, "리뷰 내용");

        Store store = new Store();
        ReflectionTestUtils.setField(store, "storeId", storeId);

        User user = new User();
        ReflectionTestUtils.setField(user, "userId", userId);

        // 가게와 사용자는 존재하는 것으로 설정
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // 주문이 존재하지 않는 상황 설정
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(RuntimeException.class, () -> reviewService.createReview(request));
        assertEquals("ID가 3인 주문을 찾을 수 없습니다.", exception.getMessage());
    }
}
