package com.sparta.tazzaofdelivery.domain.menu.controller;

import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuUpdateRequest;
import com.sparta.tazzaofdelivery.domain.menu.service.MenuService;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/stores/{storeId}/menus")
    public ResponseEntity<Object> menuCreate(@PathVariable Long storeId, @Auth AuthUser authUser, @RequestBody MenuSaveRequest request) {
        menuService.createMenu(storeId, authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/menus/{menuId}")
    public ResponseEntity<Object> menuUpdate(@PathVariable Long menuId, @Auth AuthUser authUser, @RequestBody MenuUpdateRequest request) {
        menuService.updateMenu(menuId, authUser, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<Object> deleteMenu(@PathVariable Long menuId, @Auth AuthUser authUser) {
        menuService.deleteMenu(menuId, authUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

