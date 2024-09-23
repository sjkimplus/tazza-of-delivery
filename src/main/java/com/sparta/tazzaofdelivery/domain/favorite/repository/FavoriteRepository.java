package com.sparta.tazzaofdelivery.domain.favorite.repository;

import com.sparta.tazzaofdelivery.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}