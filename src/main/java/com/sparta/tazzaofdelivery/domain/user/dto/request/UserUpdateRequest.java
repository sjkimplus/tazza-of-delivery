package com.sparta.tazzaofdelivery.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private String currentPassword;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "최소 8자리로, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함시켜주세요" )
    private String newPassword;
    private String email;
}
