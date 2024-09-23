package com.sparta.tazzaofdelivery.domain.order.orderconfig;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {

    WAIT_APPROVE(0,"승인대기"),    // 승인 대기
    PREPARE(1,"중비중"),    // 준비중
    DELIVERY(2,"배달중"),   // 배달중
    COMPLETE(3,"배달완료");   // 배달완료

    private final Integer orderCode;
    private final String orderDesc;

    OrderStatus(Integer orderCode, String orderDesc) {
        this.orderCode = orderCode;
        this.orderDesc = orderDesc;
    }

    public static OrderStatus ofCode(Integer dbData) {
        return Arrays.stream(OrderStatus.values())
                .filter(c -> c.getOrderCode().equals(dbData))
                .findAny()
                .orElseThrow(()-> new TazzaException(ErrorCode.ORDER_STATUS_CODE_NOT_FOUND));
    }
}
