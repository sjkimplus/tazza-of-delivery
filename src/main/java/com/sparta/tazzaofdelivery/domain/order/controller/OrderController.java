package com.sparta.tazzaofdelivery.domain.order.controller;

import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.domain.order.dto.request.OrderCreateRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderByOwnerResponse;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderByUserResponse;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderStatusResponse;
import com.sparta.tazzaofdelivery.domain.order.service.OrderService;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 주문 수락
    @PutMapping("/orders/{orderId}/order-approve")
    public ResponseEntity<OrderStatusResponse> approveOrder(
            @Auth AuthUser authUser,
            @PathVariable("orderId") Long orderId
    ){
        return ResponseEntity.ok(orderService.approveOrder(authUser,orderId));
    }

    // 배달 시작
    @PutMapping("/orders/{orderId}/order-deliver")
    public ResponseEntity<OrderStatusResponse> deliverOrder(
            @Auth AuthUser authUser,
            @PathVariable("orderId") Long orderId
    ){
        return ResponseEntity.ok(orderService.deliverOrder(authUser,orderId));
    }

    // 배달 완료
    @PutMapping("/orders/{orderId}/order-complete")
    public ResponseEntity<OrderStatusResponse> completeOrder(
            @Auth AuthUser authUser,
            @PathVariable("orderId") Long orderId
    ){
        return ResponseEntity.ok(orderService.completeOrder(authUser,orderId));
    }

    // user가 주문한 내역 조회
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderByUserResponse>> getAllUserOrder(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(orderService.getAllUserOrder(authUser,page,size));
    }

    // 가게에서 들어온 주문내역 조회
    @GetMapping("/orders/{storeId}")
    public ResponseEntity<Page<OrderByOwnerResponse>> getAllOwnerOrder(
            @Auth AuthUser authUser,
            @PathVariable("storeId") Long storeId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(orderService.getAllOwnerOrder(authUser, storeId, page, size));
    }


}
