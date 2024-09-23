package com.sparta.tazzaofdelivery.domain.order.repository;

import com.sparta.tazzaofdelivery.domain.order.entity.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {



}
