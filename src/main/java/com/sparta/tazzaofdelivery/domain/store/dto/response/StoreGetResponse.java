package com.sparta.tazzaofdelivery.domain.store.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class StoreGetResponse {
    private final String storeName;
    private final List<MenuResponse> menus;

    public StoreGetResponse(String storeName, List<MenuResponse> menuResponses) {
        this.storeName = storeName;
        this.menus = menuResponses;
    }
}
