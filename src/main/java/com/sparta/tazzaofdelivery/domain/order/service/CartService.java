package com.sparta.tazzaofdelivery.domain.order.service;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import com.sparta.tazzaofdelivery.domain.menu.repository.MenuRepository;
import com.sparta.tazzaofdelivery.domain.order.dto.request.CartCreateRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.response.CartCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.repository.CartRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;


    public ResponseEntity<CartCreateResponse> createCart(CartCreateRequest cartAddRequest, AuthUser authUser) {

        List<Menu[]> cart = new ArrayList<>();

        Menu addMenu = checkMenu(cartAddRequest.getMunuId());


        return null;
    }


    // 메뉴 확인
    private Menu checkMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new TazzaException(ErrorCode.MENU_NOT_FOUND));
        if(menu.isDeleted()){
            throw new TazzaException(ErrorCode.MENU_ISDELETED);
        }
        return menu;
    }
}
