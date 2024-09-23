package com.sparta.tazzaofdelivery.domain.menu.dto.request;

import com.sparta.tazzaofdelivery.domain.menu.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuSaveRequest {

    @NotBlank(message = "메뉴 이름은 필수 항목입니다.")
    private String menuName;

    @Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
    private int price;

    @NotNull(message = "카테고리는 필수 항목입니다.")
    private Category category;

}
