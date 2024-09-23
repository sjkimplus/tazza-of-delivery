package com.sparta.tazzaofdelivery.domain.order.controller;

import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.domain.order.dto.request.CartCreateRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.response.CartCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.entity.Cart;
import com.sparta.tazzaofdelivery.domain.order.service.CartService;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final RedisTemplate<String, Object> redisTemplate;

    // 장바구니 음식 담기
    @PostMapping("/carts")
    public CartCreateResponse createCart(
            @RequestBody CartCreateRequest cartCreateRequest,
            @Auth AuthUser authUser) {
        return cartService.createCart(cartCreateRequest,authUser);
    }

    // 장바구니 음식 조회
    @GetMapping("/carts")
    public CartCreateResponse getCart(){
        String key = "cart:6743345096756181903";

        CartCreateResponse response = (CartCreateResponse) redisTemplate.opsForHash().getOperations();
        return response;
    }

    //

}
