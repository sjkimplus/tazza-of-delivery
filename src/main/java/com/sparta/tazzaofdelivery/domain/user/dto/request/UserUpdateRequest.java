package com.sparta.tazzaofdelivery.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserUpdateRequest {
    @Size(min = 8, message = "비밀번호는 8자리 이상입력 하셔야 합니다")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "최소 8자리로, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함시켜주세요" )
    @NotNull(message = "필수 입력 항목입니다.")
    private String currentPassword;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "최소 8자리로, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함시켜주세요" )
    private String newPassword;

    private String email;
}
