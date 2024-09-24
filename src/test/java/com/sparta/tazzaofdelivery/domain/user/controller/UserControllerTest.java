package com.sparta.tazzaofdelivery.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.tazzaofdelivery.config.filter.AuthFilter;
import com.sparta.tazzaofdelivery.config.filter.JwtUtil;
import com.sparta.tazzaofdelivery.config.filter.MockTestFilter;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserLoginRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserLoginResponse;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSignUpResponse;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import com.sparta.tazzaofdelivery.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.mockito.Mockito.verify;

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


//    @Test
//    void User_로그인() throws Exception {
//        UserLoginRequest loginRequest = new UserLoginRequest("username", "password");
//        UserLoginResponse loginResponse = new UserLoginResponse("token");
//
//        when(userService.login(any(JwtUtil.class), any(UserLoginRequest.class), any(HttpServletResponse.class)))
//                .thenReturn(loginResponse);
//
//        mockMvc.perform(post("/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("token"));
//
//        verify(userService, times(1)).login(any(JwtUtil.class), any(UserLoginRequest.class), any(HttpServletResponse.class));
//    }

//    @Test
//    void testUpdateUser() throws Exception {
//        UserUpdateRequest updateRequest = new UserUpdateRequest("newUsername", "newEmail");
//        UserUpdateResponse updateResponse = new UserUpdateResponse(1L, "newUsername", "newEmail");
//
//        AuthUser authUser = new AuthUser(1L, "ROLE_USER");
//
//        when(userService.update(anyLong(), any(UserUpdateRequest.class))).thenReturn(updateResponse);
//
//        mockMvc.perform(put("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(updateRequest))
//                        .requestAttr("authUser", authUser))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.username").value("newUsername"))
//                .andExpect(jsonPath("$.email").value("newEmail"));
//
//        verify(userService, times(1)).update(eq(1L), any(UserUpdateRequest.class));
//    }
//
//    @Test
//    void testDeleteUser() throws Exception {
//        UserDeleteRequest deleteRequest = new UserDeleteRequest("password");
//
//        AuthUser authUser = new AuthUser(1L, "ROLE_USER");
//
//        when(userService.delete(anyLong(), any(UserDeleteRequest.class))).thenReturn("User deleted");
//
//        mockMvc.perform(delete("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(deleteRequest))
//                        .requestAttr("authUser", authUser))
//                .andExpect(status().isOk())
//                .andExpect(content().string("User deleted"));
//
//        verify(userService, times(1)).delete(eq(1L), any(UserDeleteRequest.class));
//    }
//
//    @Test
//    void testFindUser() throws Exception {
//        AuthUser authUser = new AuthUser(1L, "ROLE_USER");
//        UserSearchResponse response = new UserSearchResponse(1L, "username", "email");
//
//        when(userService.find(anyLong())).thenReturn(response);
//
//        mockMvc.perform(get("/users")
//                        .requestAttr("authUser", authUser))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.username").value("username"))
//                .andExpect(jsonPath("$.email").value("email"));
//
//        verify(userService, times(1)).find(eq(1L));
//    }
}

