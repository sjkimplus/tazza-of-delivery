package com.sparta.tazzaofdelivery.domain.user.entity;

import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import lombok.Getter;

@Getter
public class AuthUser {
    private final long id;
    private final UserType userRole;

    public AuthUser(long id, UserType userRole) {
        this.id = id;
        this.userRole = userRole;
    }
}
