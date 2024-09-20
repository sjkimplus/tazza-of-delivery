package com.sparta.tazzaofdelivery.domain.user;

import lombok.Getter;

@Getter
public class AuthUser {
    private final long id;

    public AuthUser(long id) { this.id = id; }
}
