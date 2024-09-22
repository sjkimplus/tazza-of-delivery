package com.sparta.tazzaofdelivery.domain.user.controller;

import com.sparta.tazzaofdelivery.config.filter.JwtUtil;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserLoginRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserLoginReponse;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSearchResponse;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSignUpResponse;
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

//    @PutMapping("/{userId}")
//    public ResponseEntity<UserResponseDto> update(@Auth AuthUser authUser,
//                                                  @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
//        return ResponseEntity.ok(userService.update(authUser.getEmail(), userUpdateRequestDto));
//    }
//
//    @DeleteMapping("/{userId}")
//    public String delete(@Valid @RequestBody LoginRequestDto loginRequestDto) {
//        return userService.delete(loginRequestDto);
//    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<UserSearchResponse> find(@PathVariable long id) {
//        return ResponseEntity.ok(userService.findUser(id));
//    }
}
