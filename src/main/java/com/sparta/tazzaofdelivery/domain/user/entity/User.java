package com.sparta.tazzaofdelivery.domain.user.entity;

import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserUpdateRequest;
import com.sparta.tazzaofdelivery.domain.user.enums.UserStatus;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(name = "user_pw", nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public User(UserSignUpRequest userSignUpRequest, String password) {
        this.email = userSignUpRequest.getEmail();
        this.password = password;
        this.name = userSignUpRequest.getName();
        this.userType = userSignUpRequest.getUserType();
        this.userStatus = UserStatus.ACTIVE;
    }

    public User(long id, UserType userRole) {
        this.userId = id;
        this.userType = userRole;
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    public void update(UserUpdateRequest userUpdateRequest){
        if(userUpdateRequest.getEmail() != null) this.email = userUpdateRequest.getEmail();
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getUserRole());
    }
}
