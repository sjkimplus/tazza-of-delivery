package com.sparta.tazzaofdelivery.domain.cart.controller;

import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.domain.cart.dto.request.CartCreateRequest;
import com.sparta.tazzaofdelivery.domain.cart.dto.response.CartCreateResponse;
import com.sparta.tazzaofdelivery.domain.cart.service.CartService;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 음식 담기
    @PostMapping("/carts")
    public ResponseEntity<CartCreateResponse> createCart(
            @RequestBody CartCreateRequest cartCreateRequest,
            @Auth AuthUser authUser) {
        return ResponseEntity.ok(cartService.createCart(cartCreateRequest, authUser));
    }

    // 장바구니 음식 수정
    @PutMapping("/carts/{cartId}")
    public ResponseEntity<CartCreateResponse> updateCart(
            @PathVariable("cartId") Long cartId,
            @RequestBody CartCreateRequest cartCreateRequest,
            @Auth AuthUser authUser){
        return ResponseEntity.ok(cartService.updateCart(cartId, cartCreateRequest, authUser));
    }

    // 장바구니 삭제
    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<Long> deleteCart(
            @PathVariable("cartId") Long cartId,
            @Auth AuthUser authUser
    ){
        return ResponseEntity.ok(cartService.deleteCart(cartId,authUser));
    }



}
