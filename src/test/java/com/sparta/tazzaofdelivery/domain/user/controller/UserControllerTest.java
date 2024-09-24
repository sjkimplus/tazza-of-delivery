package com.sparta.tazzaofdelivery.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.tazzaofdelivery.config.filter.JwtUtil;
import com.sparta.tazzaofdelivery.domain.user.MockSecurityConfig;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserDeleteRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserLoginRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserUpdateRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserLoginResponse;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSearchResponse;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSignUpResponse;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserUpdateResponse;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.enums.UserStatus;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import com.sparta.tazzaofdelivery.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(MockSecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void User_회원가입() throws Exception {
        UserSignUpRequest request = new UserSignUpRequest("name@email.com", "Password123!", "GoodName", UserType.OWNER);
        UserSignUpResponse response = new UserSignUpResponse("name@email.com", "Password123!", "GoodName", UserType.OWNER);

        when(userService.create(any(UserSignUpRequest.class))).thenReturn(response);

        mockMvc.perform(post("/users/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.email").value("name@email.com"))
                        .andExpect(jsonPath("$.password").value("Password123!"))
                        .andExpect(jsonPath("$.name").value("GoodName"))
                        .andExpect(jsonPath("$.userType").value("OWNER"));

        verify(userService, times(1)).create(any(UserSignUpRequest.class));
    }


    @Test
    void User_로그인() throws Exception {
        UserLoginRequest request = new UserLoginRequest("name@email.com", "Password123!");
        UserLoginResponse response = new UserLoginResponse("GoodName", "name@email.com");

        when(userService.login(any(JwtUtil.class), any(UserLoginRequest.class), any(HttpServletResponse.class)))
                .thenReturn(response);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("GoodName"))
                        .andExpect(jsonPath("$.email").value("name@email.com"));

        verify(userService, times(1)).login(any(JwtUtil.class), any(UserLoginRequest.class), any(HttpServletResponse.class));
    }

    @Test
    void User_개인정보수정_비번변경() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest("Password123!", "Password123?", null);
        UserUpdateResponse response = new UserUpdateResponse("GoodName", null, "Password123?");

        when(userService.update(anyLong(), any(UserUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .requestAttr("id", 1L)  // Simulate the "id" attribute
                        .requestAttr("type", UserType.OWNER))  // Simulate the "type" attribute
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("GoodName"))
                        .andExpect(jsonPath("$.email").doesNotExist())
                        .andExpect(jsonPath("$.updatedPassword").value("Password123?"));

        verify(userService, times(1)).update(eq(1L), any(UserUpdateRequest.class));
    }

    @Test
    void User_개인정보수정_이메일변경() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest("Password123!", null, "GoodName@email.com");
        UserUpdateResponse response = new UserUpdateResponse("GoodName", "GoodName@email.com", null);

        when(userService.update(anyLong(), any(UserUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .requestAttr("id", 1L)  // Simulate the "id" attribute
                        .requestAttr("type", UserType.OWNER))  // Simulate the "type" attribute
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GoodName"))
                .andExpect(jsonPath("$.email").value("GoodName@email.com"))
                .andExpect(jsonPath("$.updatedPassword").doesNotExist());

        verify(userService, times(1)).update(eq(1L), any(UserUpdateRequest.class));
    }

    @Test
    void User_유저삭제() throws Exception {
        UserDeleteRequest request = new UserDeleteRequest("Password123!");
        String result = "삭제 완료";

        AuthUser authUser = new AuthUser(1L, UserType.OWNER);

        when(userService.delete(authUser.getId(), request)).thenReturn(result);

        mockMvc.perform(delete("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .requestAttr("id", 1L)  // Simulate the "id" attribute
                        .requestAttr("type", UserType.OWNER))  // Simulate the "type" attribute
                        .andExpect(status().isOk());

        verify(userService, times(1)).delete(eq(1L), any(UserDeleteRequest.class));
    }

    @Test
    void User_본인_프로필_검색() throws Exception {
        AuthUser authUser = new AuthUser(1L, UserType.CUSTOMER);

        LocalDateTime timeCreated = LocalDateTime.now();
        UserSearchResponse response = new UserSearchResponse("goodName@email.com", "GoodName", UserType.CUSTOMER, UserStatus.ACTIVE, timeCreated);

        when(userService.find(authUser.getId())).thenReturn(response);

        mockMvc.perform(get("/users")
                        .requestAttr("id", 1L)
                        .requestAttr("type", UserType.CUSTOMER))
                .andExpect(status().isOk());
        verify(userService, times(1)).find(eq(authUser.getId()));
    }
}

