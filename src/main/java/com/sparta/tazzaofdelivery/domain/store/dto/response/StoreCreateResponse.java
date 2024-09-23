package com.sparta.tazzaofdelivery.domain.store.dto.response;

import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreCreateResponse {
    private final String storeName;
    private final LocalTime createdAt;
    private final LocalTime closedAt;
    private final Long minimumOrderQuantity;
    private final String storeAnnouncement;
    private final StoreStatus storeStatus;
;
    public StoreCreateResponse(String storeName, LocalTime createdAt, LocalTime closedAt, Long minimumOrderQuantity, String storeAnnouncement, StoreStatus storeStatus) {
        this.storeName = storeName;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.minimumOrderQuantity = minimumOrderQuantity;
        this.storeAnnouncement = storeAnnouncement;
        this.storeStatus = storeStatus;
    }
}
