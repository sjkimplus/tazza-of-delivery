package com.sparta.tazzaofdelivery.domain.store.service;

import com.sparta.tazzaofdelivery.domain.store.dto.request.StoreCreateRequest;
import com.sparta.tazzaofdelivery.domain.store.repository.StoreRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    void createStore() {
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = User.fromAuthUser(authUser);

//        StoreCreateRequest request = new StoreCreateRequest("아아", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지");



    }

    @Test
    void getAllStores() {
    }

    @Test
    void deleteStore() {
    }
}