package com.sparta.tazzaofdelivery.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateRequest {
    private String storeName;
    private LocalTime createdAt;
    private LocalTime closedAt;
    private Long minimumOrderQuantity;
    private String storeAnnouncement;
}
