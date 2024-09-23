package com.sparta.tazzaofdelivery.domain.user.dto.response;

import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.enums.UserStatus;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSearchResponse {
    // email, name, usertype, user status, created at
    private String email;
    private String name;
    private UserType userType;
    private UserStatus userStatus;
    private LocalDateTime createdAt;

    public UserSearchResponse(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.userType = user.getUserType();
        this.userStatus = user.getUserStatus();
        this.createdAt = user.getCreatedAt();
    }
}
