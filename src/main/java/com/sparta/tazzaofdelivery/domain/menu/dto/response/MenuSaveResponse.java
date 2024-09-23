package com.sparta.tazzaofdelivery.domain.menu.dto.response;

import com.sparta.tazzaofdelivery.domain.menu.entity.Category;
import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuSaveResponse {

    private final String menuName;
    private final int price;
    private final Category category;

    public MenuSaveResponse(Menu menu){
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.category = menu.getCategory();
    }

}
