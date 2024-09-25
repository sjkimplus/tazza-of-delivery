package com.sparta.tazzaofdelivery.domain.cart.repository;

import com.sparta.tazzaofdelivery.domain.cart.entity.Cart;
import com.sparta.tazzaofdelivery.domain.cart.entity.CartStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT c FROM Cart c WHERE c.userId = :userId AND c.cartStatus = :cartStatus")
    Optional<List<Cart>> findValidCartsByUserId(@Param("userId")Long userId, @Param("cartStatus")CartStatus cartStatus);
}
