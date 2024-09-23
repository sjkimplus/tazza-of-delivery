package com.sparta.tazzaofdelivery.domain.order.controller;

import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.domain.order.dto.request.OrderCreateRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.service.OrderService;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponse> createOrder(
            @Auth AuthUser authUser,
            @RequestBody OrderCreateRequest orderCreateRequest) {
        return ResponseEntity.ok(orderService.createOrder(authUser, orderCreateRequest));
    }

}
