package com.sparta.tazzaofdelivery.domain.user.dto.response;

import com.sparta.tazzaofdelivery.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateResponse {

    private String name;
    private String email;
    private String updatedPassword;

    public UserUpdateResponse(User user, String newPassword) {
        this.name = user.getName();
        this.email = user.getEmail();
        if (newPassword!=null) this.updatedPassword = newPassword;
    }
}
