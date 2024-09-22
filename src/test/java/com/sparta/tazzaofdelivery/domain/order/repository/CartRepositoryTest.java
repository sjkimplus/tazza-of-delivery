package com.sparta.tazzaofdelivery.domain.order.repository;

import com.sparta.tazzaofdelivery.domain.order.entity.Cart;
import com.sparta.tazzaofdelivery.domain.order.orderconfig.CartStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;



@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    @DisplayName("Redis Entity 저장 TEST")
    void RedisEntitySaveTest() {
        Cart cart = Cart.builder()
                .cartId(1L)
                .storeId(1L)
                .userId(2L)
                .menu("{name: 치킨, price:15000}")
                .cartCreated(LocalDateTime.now())
                .cartStatus(CartStatus.VALID)
                .build();
        cartRepository.save(cart);
        System.out.println("::: 장바구니 저장 완료 :::");

    }

    @Test
    @DisplayName("Redis Entity 조회 TEST")
    void RedisEntityGetTest() {
        Cart cart = cartRepository.findById(1L).get();
        System.out.println(cart.getCartId());
        System.out.println(cart.getStoreId());
        System.out.println(cart.getUserId());
        System.out.println(cart.getMenu());
        System.out.println(cart.getCartCreated());
        System.out.println(cart.getCartStatus());
        System.out.println("::: 장바구니 조회 완료 :::");

    }
}