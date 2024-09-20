package com.sparta.tazzaofdelivery.domain.user;


public enum UserType {
    CUSTOMER(Authority.CUSTOMER),
    OWNER(Authority.OWNER);

    private final String authority;

    UserType(String authority) {
        this.authority = authority;
    }

    public String getUserType() {
        return this.authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "USER_CUSTOMER";
        public static final String OWNER = "USER_OWNER";
    }
}
