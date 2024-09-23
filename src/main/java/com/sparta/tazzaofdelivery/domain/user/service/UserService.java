package com.sparta.tazzaofdelivery.domain.user.service;

import com.sparta.tazzaofdelivery.config.filter.JwtUtil;
import com.sparta.tazzaofdelivery.config.passwordconfig.PasswordEncoder;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserLoginRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserUpdateRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserLoginReponse;
import com.sparta.tazzaofdelivery.domain.user.dto.request.UserSignUpRequest;
import com.sparta.tazzaofdelivery.domain.user.dto.response.UserSignUpResponse;

import com.sparta.tazzaofdelivery.domain.user.dto.response.UserUpdateResponse;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.tazzaofdelivery.domain.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserSignUpResponse create(UserSignUpRequest userSignUpRequest) {
        String password = passwordEncoder.encode(userSignUpRequest.getPassword());
        String email = userSignUpRequest.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) throw new TazzaException(USER_ID_DUPLICATION);

        // 유저 생성
        User user = new User(userSignUpRequest, password);
        userRepository.save(user);

        return new UserSignUpResponse(userSignUpRequest);
    }

    public UserLoginReponse login(JwtUtil jwtUtil, UserLoginRequest userLoginRequest, HttpServletResponse httpServletResponse) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new TazzaException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new TazzaException(USER_PW_ERROR);
        }


        String token = jwtUtil.createToken(user.getUserId(), user.getEmail());
        jwtUtil.addJwtToCookie(token, httpServletResponse);
        return new UserLoginReponse(user);
    }

    @Transactional
    public UserUpdateResponse update(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new TazzaException(USER_NOT_FOUND)
        );

        if (userUpdateRequest.getNewPassword() != null) {
            if (!passwordEncoder.matches(userUpdateRequest.getCurrentPassword(), user.getPassword())) {
                throw new TazzaException(USER_PW_ERROR);
            }
            if (user.getPassword().equals(userUpdateRequest.getNewPassword())) {
                throw new TazzaException(USER_SAME_PW_ERROR);
            }
            String password = passwordEncoder.encode(userUpdateRequest.getNewPassword());
            user.updatePassword(password);
        }
        user.update(userUpdateRequest);
        return new UserUpdateResponse(user, userUpdateRequest.getNewPassword());
    }

//    @Transactional
//    public String delete(LoginRequestDto loginRequestDto) {
//        User user = findUser(loginRequestDto.getEmail());
//
//        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
//            throw new PasswordMismatchException(loginRequestDto.getEmail() + " 의 " + "패스워드가 올바르지 않습니다.");
//        }
//
//        user.deleteUpdate(java.time.LocalDateTime.now());
//
//        return "삭제 완료";
//    }

//    public User getUser(long id) {
//        User user = userRepository.findById(id).orElseThrow(() ->  new TazzaException(USER_NOT_FOUND));
//        if (user.getDeletedAt() != null) {
//            throw new TazzaException(USER_NOT_FOUND);
//        }
//
//        return user;
//    }
//
//    public UserResponseDto findProfile(String email) {
//        User user = getUser(email);
//
//        List<String> imageUrls = fileUtils.getImage(USER, user.getId());
//
//        return new UserResponseDto(user, imageUrls);
//    }



}

