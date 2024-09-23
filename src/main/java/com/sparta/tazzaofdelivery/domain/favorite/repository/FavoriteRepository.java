package com.sparta.tazzaofdelivery.domain.favorite.repository;

import com.sparta.tazzaofdelivery.domain.favorite.entity.Favorite;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserAndStore(User user, Store store);
    Optional<Favorite> findByUserAndStore(User user, Store store);
    List<Favorite> findAllByUser(User user);
}
