package com.sparta.tazzaofdelivery.domain.favorite.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
public class FavoriteGetResponse {
    private final String storeName;
    private final LocalTime createdAt;
    private final LocalTime closedAt;

    public FavoriteGetResponse(String storeName, LocalTime createdAt, LocalTime closedAt) {
        this.storeName = storeName;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
    }
}
