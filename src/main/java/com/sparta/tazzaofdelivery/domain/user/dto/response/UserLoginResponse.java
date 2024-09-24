package com.sparta.tazzaofdelivery.domain.user.dto.response;

import com.sparta.tazzaofdelivery.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private String name;
    private String email;

    public UserLoginResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
