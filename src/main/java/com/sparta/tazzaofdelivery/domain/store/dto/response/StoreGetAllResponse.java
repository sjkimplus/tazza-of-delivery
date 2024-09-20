package com.sparta.tazzaofdelivery.domain.store.dto.response;

import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreGetAllResponse {
    private final String storeName;
    private final LocalTime createdAt;
    private final LocalTime closedAt;
    private final StoreStatus storeStatus;


    public StoreGetAllResponse(String storeName, LocalTime createdAt, LocalTime closedAt, StoreStatus status) {
        this.storeName = storeName;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.storeStatus = status;
    }
}
