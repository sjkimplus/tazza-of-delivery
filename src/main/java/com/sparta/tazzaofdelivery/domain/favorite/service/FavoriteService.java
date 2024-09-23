package com.sparta.tazzaofdelivery.domain.favorite.service;

import com.sparta.tazzaofdelivery.domain.favorite.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;


}
