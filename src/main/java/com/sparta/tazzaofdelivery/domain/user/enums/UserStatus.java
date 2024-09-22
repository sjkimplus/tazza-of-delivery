package com.sparta.tazzaofdelivery.domain.user.enums;

public enum UserStatus {
    ACTIVE(UserStatus.Authority.ACTIVE),
    DELETED(UserStatus.Authority.DELETED);

    private final String authority;

    UserStatus(String authority) {
        this.authority = authority;
    }

    public String getUserStatus() {
        return this.authority;
    }

    public static class Authority {
        public static final String ACTIVE = "USER_ACTIVE";
        public static final String DELETED = "USER_DELETED";
    }
}
