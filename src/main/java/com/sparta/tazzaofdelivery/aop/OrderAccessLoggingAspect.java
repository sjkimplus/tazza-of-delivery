package com.sparta.tazzaofdelivery.aop;

import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderStatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class OrderAccessLoggingAspect {

    @Pointcut("execution(* com.sparta.tazzaofdelivery.domain.order.controller.OrderController.createOrder(..))")
    private void OrderCreateAccessLogControllerLayer() {}

    @Pointcut("execution(* com.sparta.tazzaofdelivery.domain.order.controller.OrderController.approveOrder(..)) || execution(* com.sparta.tazzaofdelivery.domain.order.controller.OrderController.deliverOrder(..)) || execution(* com.sparta.tazzaofdelivery.domain.order.controller.OrderController.completeOrder(..))")
    private void OrderStatusAccessLogControllerLayer() {}

    @Around("OrderCreateAccessLogControllerLayer()")
    public void beforeOrderAccessLogController(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Object responsObject = joinPoint.proceed();

        ResponseEntity<OrderCreateResponse> orderCreateResponse = (ResponseEntity<OrderCreateResponse>) responsObject;

        String sotreName = orderCreateResponse.getBody().getStoreName();
//        log.info(sotreName);


        Long StoreId = orderCreateResponse.getBody().getStoreId();
        Long OrderId = orderCreateResponse.getBody().getOrderId();
        Long UserId = (Long) request.getAttribute("id");
        LocalDateTime requestTime = LocalDateTime.now();

        log.info("::: Order Access Log ::: - RequestTime : {}, User ID : {}, Store ID : {}, Order ID : {}",
                requestTime, UserId, StoreId, OrderId);

    }

    @Around("OrderStatusAccessLogControllerLayer()")
    public void beforeOrderStatusAccessLogController(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Object responsObject = joinPoint.proceed();

        ResponseEntity<OrderStatusResponse> orderCreateResponse = (ResponseEntity<OrderStatusResponse>) responsObject;

        Long StoreId = orderCreateResponse.getBody().getStoreId();
        Long OrderId = orderCreateResponse.getBody().getOrderId();
        Long UserId = (Long) request.getAttribute("id");
        LocalDateTime requestTime = LocalDateTime.now();

        log.info("::: Order Access Log ::: - RequestTime : {}, User ID : {}, Store ID : {}, Order ID : {}",
                requestTime, UserId, StoreId, OrderId);
    }

}
