package com.sparta.tazzaofdelivery.domain.favorite.service;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.favorite.dto.response.FavoriteGetResponse;
import com.sparta.tazzaofdelivery.domain.favorite.entity.Favorite;
import com.sparta.tazzaofdelivery.domain.favorite.repository.FavoriteRepository;
import com.sparta.tazzaofdelivery.domain.store.dto.response.StoreGetAllResponse;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.store.repository.StoreRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;


    public void addFavorite(Long storeId, AuthUser authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new TazzaException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new TazzaException(ErrorCode.STORE_NOT_FOUND));

        if(favoriteRepository.existsByUserAndStore(user, store)){
            throw new TazzaException(ErrorCode.ALREADY_FAVORITE);
        }

        Favorite favorite = new Favorite(user, store);
        favoriteRepository.save(favorite);
    }




    public void removeFavorite(Long storeId, AuthUser authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new TazzaException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new TazzaException(ErrorCode.STORE_NOT_FOUND));

        Favorite favorite = favoriteRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new TazzaException(ErrorCode.FAVORITE_NOT_FOUND));

        favorite.setIsDeleted(true);
        favoriteRepository.save(favorite);
    }

    public List<FavoriteGetResponse> getAllFavorites(AuthUser authUser) {
        User user = User.fromAuthUser(authUser);
        List<Favorite> favoriteStores = favoriteRepository.findAllByUser(user);

        List<FavoriteGetResponse> favoriteResponses = new ArrayList<>();
        for (Favorite favorite : favoriteStores) {
            Store store = favorite.getStore();
            FavoriteGetResponse response = new FavoriteGetResponse(
                    store.getStoreName(),
                    store.getCreatedAt(),
                    store.getClosedAt()
            );
            favoriteResponses.add(response);
        }
        return favoriteResponses;
    }
}
