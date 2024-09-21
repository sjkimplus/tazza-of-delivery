package com.sparta.tazzaofdelivery.domain.menu.service;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuUpdateRequest;
import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
import com.sparta.tazzaofdelivery.domain.menu.repository.MenuRepository;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.store.repository.StoreRepository;
import com.sparta.tazzaofdelivery.domain.user.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public void createMenu(Long storeId, AuthUser authUser, MenuSaveRequest request) {

        Store store = checkStore(storeId);

        authCheck(authUser, store);

        Menu menu = new Menu(request);

        menuRepository.save(menu);

    }

    public void updateMenu(Long menuId, AuthUser authUser, MenuUpdateRequest request) {

        Menu menu = checkMenu(menuId);

        authCheck(authUser, menu.getStore());

        menu.update(request);

    }

    public void deleteMenu(Long menuId, AuthUser authUser) {

        Menu menu = checkMenu(menuId);

        authCheck(authUser, menu.getStore());

        menuRepository.delete(menu);

    }


    // 메뉴 확인
    private Menu checkMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new TazzaException(ErrorCode.MENU_NOT_FOUND));
        return menu;
    }

    // 가게 사장님 권한확인
    private void authCheck(AuthUser authUser, Store store) {
        if (!Objects.equals(store.getUser().getId(), authUser.getId())) {
            throw new TazzaException(ErrorCode.MENU_CREATE_UNAUTHORIZED);
        }
    }

    // 가게 확인
    private Store checkStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new TazzaException(ErrorCode.STORE_NOT_FOUND));
        return store;
    }

}
