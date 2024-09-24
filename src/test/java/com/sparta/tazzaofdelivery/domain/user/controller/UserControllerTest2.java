//package com.sparta.tazzaofdelivery.domain.user.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.tazzaofdelivery.config.filter.JwtUtil;
//import com.sparta.tazzaofdelivery.domain.user.dto.request.UserDeleteRequest;
//import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
//import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSignUpResponse;
//import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
//import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
//import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
//import com.sparta.tazzaofdelivery.domain.user.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
////@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc
//public class UserControllerTest2 {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserController userController;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @Test
//    void signup() throws Exception {
//        UserSignUpRequest request = new UserSignUpRequest("name@email.com", "Password123!", "GoodName", UserType.OWNER);
//        UserSignUpResponse response = new UserSignUpResponse("name@email.com", "Password123!", "GoodName", UserType.OWNER);
//
//        given(userService.create(any(UserSignUpRequest.class))).willReturn(response);
//
//        mockMvc.perform(post("/users/sign-up")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("name@email.com"))
//                .andExpect(jsonPath("$.password").value("Password123!"))
//                .andExpect(jsonPath("$.name").value("GoodName"))
//                .andExpect(jsonPath("$.userType").value("OWNER"));
//
//        verify(userService, times(1)).create(any(UserSignUpRequest.class));
//
//    }
//
//    @Test
//    void testDeleteUser() throws Exception {
//        UserDeleteRequest request = new UserDeleteRequest("Password123!", "Sungju");
//        String result = "삭제 완료";
//
//        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
//
//        when(userService.delete(authUser.getId(), request)).thenReturn("삭제 완료");
//
//        mockMvc.perform(delete("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request))
////                        .requestAttr("id", authUser.getId())  // Simulate the "id" attribute
////                        .requestAttr("type", authUser.getUserRole()))  // Simulate the "type" attribute
//                        .requestAttr("authUser", authUser))
//                .andExpect(status().isOk());
////                .andExpect(content().string(result));
//
////        verify(userService, times(1)).delete(eq(1L), any(UserDeleteRequest.class));
//    }
//
//
//}
