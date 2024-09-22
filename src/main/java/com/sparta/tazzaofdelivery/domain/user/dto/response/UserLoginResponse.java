package com.sparta.tazzaofdelivery.domain.user.dto.response;

import com.sparta.tazzaofdelivery.domain.user.dto.request.UserLoginRequest;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserLoginResponse {
    private String name;
    private String email;

    public UserLoginResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
