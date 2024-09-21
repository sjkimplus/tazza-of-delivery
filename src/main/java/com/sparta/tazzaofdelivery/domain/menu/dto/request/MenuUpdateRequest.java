package com.sparta.tazzaofdelivery.domain.menu.dto.request;

import com.sparta.tazzaofdelivery.domain.menu.entity.Category;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuUpdateRequest {

    private String menuName;

    @Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
    private Integer price;

    private Category category;

}
