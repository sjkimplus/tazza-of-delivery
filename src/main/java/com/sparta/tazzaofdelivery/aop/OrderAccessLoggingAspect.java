package com.sparta.tazzaofdelivery.aop;

import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderCreateResponse;
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

    @Pointcut("execution(* com.sparta.tazzaofdelivery.domain.order.controller.OrderController.createOrder(..)) || execution(* com.sparta.tazzaofdelivery.domain.order.controller.OrderController.approveOrder(..)) || execution(* com.sparta.tazzaofdelivery.domain.order.controller.OrderController.deliverOrder(..)) || execution(* com.sparta.tazzaofdelivery.domain.order.controller.OrderController.completeOrder(..))")
    private void OrderAccessLogControllerLayer(){

    }

    @Around("OrderAccessLogControllerLayer()")
    public void beforeOrderAccessLogController(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletResponse response = (HttpServletResponse)joinPoint.getArgs()[0];

        Object responsObject = joinPoint.proceed();


        Object responseTest = joinPoint.getArgs()[0];
        log.info(responseTest.toString());

        ResponseEntity<OrderCreateResponse> orderCreateResponse = (ResponseEntity<OrderCreateResponse>) responsObject;
        String StoreId = orderCreateResponse.getBody().getStoreName();
        log.info(StoreId);

        LocalDateTime requestTime = LocalDateTime.now();
        Long UserId = (Long) request.getAttribute("id");

        log.info(":::TEST userId : {}, time: {}", UserId, requestTime);

//        HttpServletResponse response =
//
//        log.info("::: Order Access Log ::: - RequestTime : {}, User ID : {}, Store ID : {}",
//                requestTime, )

    }

}
