package com.sparta.tazzaofdelivery.domain.order.repository;

import com.sparta.tazzaofdelivery.domain.order.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
}
