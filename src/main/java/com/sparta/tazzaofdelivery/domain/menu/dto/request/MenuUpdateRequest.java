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

    private Integer price;

    private Category category;

}
