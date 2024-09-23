package com.sparta.tazzaofdelivery.domain.user.controller;

import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.config.filter.JwtUtil;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserDeleteRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserLoginRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserUpdateRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserLoginReponse;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSearchResponse;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSignUpResponse;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserUpdateResponse;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<UserSignUpResponse> create(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        return ResponseEntity.ok(userService.create(userSignUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginReponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok(userService.login(jwtUtil, userLoginRequest, httpServletResponse));
    }

    @PutMapping
    public ResponseEntity<UserUpdateResponse> update(@Auth AuthUser authUser,
                                                     @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.update(authUser.getId(), userUpdateRequest));
    }

    @DeleteMapping
    public String delete(@Auth AuthUser authUser, @Valid @RequestBody UserDeleteRequest userDeleteRequest) {
        return userService.delete(authUser.getId(), userDeleteRequest);
    }

    @GetMapping
    public ResponseEntity<UserSearchResponse> find(@Auth AuthUser authUser) {
        System.out.println("authUser type = " + authUser.getUserRole());
        return ResponseEntity.ok(userService.find(authUser.getId()));
    }
}
