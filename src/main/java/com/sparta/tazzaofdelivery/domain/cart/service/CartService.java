package com.sparta.tazzaofdelivery.domain.cart.service;

import com.sparta.tazzaofdelivery.domain.cart.entity.CartStatus;
import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import com.sparta.tazzaofdelivery.domain.menu.repository.MenuRepository;
import com.sparta.tazzaofdelivery.domain.cart.dto.request.CartCreateRequest;
import com.sparta.tazzaofdelivery.domain.cart.dto.response.CartCreateResponse;
import com.sparta.tazzaofdelivery.domain.cart.entity.Cart;
import com.sparta.tazzaofdelivery.domain.cart.repository.CartRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    

    // 장바구니 담기
    public CartCreateResponse createCart(CartCreateRequest cartCreateRequest, AuthUser authUser) {
        // 주문한 메뉴 찾기
        Menu orderMenu = checkMenu(cartCreateRequest.getMenuId());
        // 주문한 메뉴 개수
        Long menuCount = cartCreateRequest.getCount();
        // 사용자 찾기
        User orderUser = findUserByUserId(authUser.getId());

        Cart newCart = new Cart(
                orderMenu.getStore().getStoreId(),
                orderUser.getUserId(),
                orderMenu.getMenuId(),
                menuCount,
                LocalDateTime.now(),
                CartStatus.VALID
        );

        Cart savedCart = cartRepository.save(newCart);

        return new CartCreateResponse(
                savedCart.getCartId(),
                savedCart.getStoreId(),
                savedCart.getUserId(),
                savedCart.getMenuId(),
                savedCart.getMenuCount(),
                savedCart.getCartCreatedAt(),
                savedCart.getCartStatus()
        );
    }

    // 장바구니 음식 수정
    public CartCreateResponse updateCart(Long cartId, CartCreateRequest cartCreateRequest, AuthUser authUser) {
        Cart cart = cartUserAuthentication(authUser.getId(), cartId);

        cart.update(
                cartCreateRequest.getMenuId(),
                cartCreateRequest.getCount()
        );

        return CartCreateResponse.builder()
                .cartId(cart.getCartId())
                .storeId(cart.getStoreId())
                .userId(cart.getUserId())
                .menuId(cart.getMenuId())
                .menuCount(cart.getMenuCount())
                .cartCreatedAt(cart.getCartCreatedAt())
                .cartStatus(cart.getCartStatus())
                .build();

    }

    // 장바구니 삭제
    public Long deleteCart(Long cartId, AuthUser authUser) {
        Cart cart = cartUserAuthentication(authUser.getId(), cartId);
        cartRepository.delete(cart);
        return cart.getCartId();
    }


    // 장바구니 확인
    private Cart findCartByCartId(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(()-> new TazzaException(ErrorCode.CART_NOT_FOUND));
    }

    // 사용자 확인
    private User findUserByUserId(long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new TazzaException(ErrorCode.USER_NOT_FOUND));
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

    // 장바구니 사용자 확인
    private Cart cartUserAuthentication(Long userId, Long cartId) {
        User user = findUserByUserId(userId);
        Cart cart = findCartByCartId(cartId);

        if(!cart.getUserId().equals(user.getUserId())){
            throw new TazzaException(ErrorCode.CART_USER_NOT_EQUAL);
        }
        return cart;
    }



}
