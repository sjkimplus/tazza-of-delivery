package com.sparta.tazzaofdelivery.domain.order.service;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import com.sparta.tazzaofdelivery.domain.menu.repository.MenuRepository;
import com.sparta.tazzaofdelivery.domain.order.dto.request.CartCreateRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.response.CartCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.entity.Cart;
import com.sparta.tazzaofdelivery.domain.order.orderconfig.CartStatus;
import com.sparta.tazzaofdelivery.domain.order.repository.CartRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    

    public CartCreateResponse createCart(CartCreateRequest cartCreateRequest, AuthUser authUser) {
        // 주문한 메뉴 찾기
        Menu orderMenu = checkMenu(cartCreateRequest.getMunuId());
        // 주문한 메뉴 개수
        Long menuCount = cartCreateRequest.getCount();
        // 사용자 찾기
        User orderUser = findUserByUserId(authUser.getId());

        String menu = orderMenu.toString();

        // 해당 user의 유요한 장바구니가 존재하는지 확인
//        Cart userCart = checkUserHasCart(orderUser.getUserId());

//        var key = "keyword:Object:%s".formatted(userCart.getCartId());
        Cart newCart = new Cart(
                orderMenu.getStore().getStoreId(),
                orderUser.getUserId(),
                menu,
                LocalDateTime.now(),
                CartStatus.VALID);

        Cart saveCart = cartRepository.save(newCart);

        return new CartCreateResponse(
                saveCart.getCartId(),
                saveCart.getStoreId(),
                saveCart.getUserId(),
                saveCart.getMenuOrder(),
                saveCart.getCartCreatedAt(),
                saveCart.getCartStatus()
        );
    }

    private Cart checkUserHasCart(Long userId) {
        return cartRepository.findById(userId).orElseThrow(()->new TazzaException(ErrorCode.CART_USER_NOT_EXIST));
    }

    private User findUserByUserId(long id) {
        return userRepository.findById(id).orElseThrow(()->new TazzaException(ErrorCode.USER_NOT_FOUND));
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
