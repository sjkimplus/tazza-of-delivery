package com.sparta.tazzaofdelivery.domain.store.dto.response;

import com.sparta.tazzaofdelivery.domain.menu.dto.response.MenuSaveResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreGetResponse {
    private final String storeName;
    private final List<MenuSaveResponse> menus;

    public StoreGetResponse(String storeName, List<MenuSaveResponse> menuResponses) {
        this.storeName = storeName;
        this.menus = menuResponses;
    }
}
