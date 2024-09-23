package com.sparta.tazzaofdelivery.domain.favorite.controller;


import com.sparta.tazzaofdelivery.config.annotation.Auth;
import com.sparta.tazzaofdelivery.domain.favorite.dto.response.FavoriteGetResponse;
import com.sparta.tazzaofdelivery.domain.favorite.entity.Favorite;
import com.sparta.tazzaofdelivery.domain.favorite.repository.FavoriteRepository;
import com.sparta.tazzaofdelivery.domain.favorite.service.FavoriteService;
import com.sparta.tazzaofdelivery.domain.store.dto.response.StoreGetAllResponse;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.store.service.StoreService;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final FavoriteRepository favoriteRepository;

    @PostMapping("/stores/{storeId}/favorites")
    public ResponseEntity<String> addFavorite(@PathVariable Long storeId, @Auth AuthUser authUser) {
        favoriteService.addFavorite(storeId, authUser);
        return ResponseEntity.ok("즐겨찾기가 추가 되었습니다.");
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteGetResponse>> getFavorites(@Auth AuthUser authUser) {
        return ResponseEntity.ok(favoriteService.getAllFavorites(authUser));
    }

    @DeleteMapping("/stores/{storeId}/favorites")
    public ResponseEntity<String> removeFavorite(@PathVariable Long storeId, @Auth AuthUser authUser) {
        favoriteService.removeFavorite(storeId, authUser);
        return ResponseEntity.ok("즐겨찾기가 삭제 되었습니다.");
    }
}
