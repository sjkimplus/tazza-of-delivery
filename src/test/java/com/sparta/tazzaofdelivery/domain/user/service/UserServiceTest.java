package com.sparta.tazzaofdelivery.domain.user.service;

import com.sparta.tazzaofdelivery.config.filter.JwtUtil;
import com.sparta.tazzaofdelivery.config.passwordconfig.PasswordEncoder;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserLoginRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserLoginResponse;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSignUpResponse;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Test
    void User_생성_성공() {
        // Given
        String rawPassword = "Password123!";
        String encodedPassword = "encodedPassword";
        UserSignUpRequest request = new UserSignUpRequest("name@email.com", "Password123!", "GoodName", UserType.OWNER);


        // Mocking password encoding and email check
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // When
        UserSignUpResponse response = userService.create(request);

        // Then
        assertNotNull(response);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void User_생성_실패_이메일_중복() {
        // Given
        UserSignUpRequest request = new UserSignUpRequest("name@email.com", "Password123!", "GoodName", UserType.OWNER);
        User existingUser = new User();

        // Mocking an existing user for email duplication
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        // When / Then
        assertThrows(TazzaException.class, () -> userService.create(request));
        verify(userRepository, never()).save(any(User.class));  // Ensure save is not called
    }

    @Test
    void User_로그인_성공() {
        // Given
        UserSignUpRequest signUpRequest = new UserSignUpRequest("name@email.com", "Password123!", "GoodName", UserType.OWNER);
        User user = new User(signUpRequest,"Password123!");
        UserLoginRequest loginRequest = new UserLoginRequest("name@email.com", "Password123!");

        String token = "jwtToken";

        // Mocking repository and password encoding behavior
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.createToken(user.getUserId(), user.getEmail())).thenReturn(token);

        // When
        UserLoginResponse response = userService.login(jwtUtil, loginRequest, httpServletResponse);

        // Then
        assertNotNull(response);
        assertEquals(loginRequest.getEmail(), response.getEmail());
        verify(jwtUtil).createToken(user.getUserId(), user.getEmail());
        verify(jwtUtil).addJwtToCookie(token, httpServletResponse);
    }

    @Test
    void User_로그인_실패_유저_존재하지_않음() {
        // Given
        UserLoginRequest request = new UserLoginRequest("name@email.com", "Password123!");

        // Mocking repository to return empty
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // When / Then
        assertThrows(TazzaException.class, () -> userService.login(jwtUtil, request, httpServletResponse));
        verify(jwtUtil, never()).createToken(anyLong(), anyString());
        verify(jwtUtil, never()).addJwtToCookie(anyString(), any(HttpServletResponse.class));
    }
}
