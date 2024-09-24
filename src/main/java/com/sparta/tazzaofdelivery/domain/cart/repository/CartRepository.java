package com.sparta.tazzaofdelivery.domain.cart.repository;

import com.sparta.tazzaofdelivery.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface CartRepository extends JpaRepository<Cart, Long> {



}
