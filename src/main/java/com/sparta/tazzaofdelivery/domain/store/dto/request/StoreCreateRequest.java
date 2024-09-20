package com.sparta.tazzaofdelivery.domain.store.dto.request;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreCreateRequest {
    private String storeName;
    private LocalTime createdAt;
    private LocalTime closedAt;
    private Long minimumOrderQuantity;
    private String storeAnnouncement;
}
