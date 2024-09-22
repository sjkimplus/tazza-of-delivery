package com.sparta.tazzaofdelivery.domain.order.controller;

import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.domain.order.dto.request.CartCreateRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.response.CartCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.service.CartService;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/carts")
    public ResponseEntity<CartCreateResponse> createCart(
            @RequestBody CartCreateRequest cartAddRequest,
            @Auth AuthUser authUser) {
        return cartService.createCart(cartAddRequest,authUser);
    }

}
