package com.sparta.tazzaofdelivery.domain.user.dto.response;

import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import lombok.Getter;

@Getter
public class UserSignUpResponse {
    private String email;
    private String password;
    private String name;
    private UserType userType;

    public UserSignUpResponse(UserSignUpRequest userSignUpRequest) {
        this.email = userSignUpRequest.getEmail();
        this.password = userSignUpRequest.getPassword();
        this.name = userSignUpRequest.getName();
        this.userType = userSignUpRequest.getUserType();
    }
}
