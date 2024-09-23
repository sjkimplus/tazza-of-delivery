package com.sparta.tazzaofdelivery.domain.order.controller;

import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.domain.order.dto.request.CartAddFoodRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.request.CartCreateRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.response.CartAddFoodResponse;
import com.sparta.tazzaofdelivery.domain.order.dto.response.CartCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.entity.Cart;
import com.sparta.tazzaofdelivery.domain.order.service.CartService;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final RedisTemplate<String, Object> redisTemplate;

    // 장바구니 음식 담기
    @PostMapping("/carts")
    public ResponseEntity<CartCreateResponse> createCart(
            @RequestBody CartCreateRequest cartCreateRequest,
            @Auth AuthUser authUser) {
        return ResponseEntity.ok(cartService.createCart(cartCreateRequest, authUser));
    }

    // 장바구니 음식 조회
    @GetMapping("/carts")
    public Map<Object,Object> getCart(){
        String key = "6743345096756181903";

        return redisTemplate.opsForHash().entries(key);
    }

    @GetMapping("/carts/test")
    public Set<Object> getCartTest(){
        return redisTemplate.opsForHash().keys("6743345096756181903");
    }

    // 장바구니 음식 추가
    @PutMapping("/carts/{cartId}")
    public CartAddFoodResponse addFoodCart(
            @PathVariable("cartId") Long cartId,
            @RequestBody CartAddFoodRequest cartAddFoodRequest,
            @Auth AuthUser authUser){
        return cartService.addFoodCartf(cartId,cartAddFoodRequest,authUser);
    }

    // 장바구니 삭제
    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<Void> deleteCart(
            @PathVariable("cartId") Long cartId,
            @Auth AuthUser authUser){
        cartService.deleteCart(cartId,authUser);
        return ResponseEntity.ok().build();
    }


}
