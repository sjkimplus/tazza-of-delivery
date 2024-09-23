package com.sparta.tazzaofdelivery.domain.store.dto.response;

import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class StoreIntegratedResponse {
    private final String storeName;
    private final LocalTime createdAt;
    private final LocalTime closedAt;
    private final StoreStatus status;
}
