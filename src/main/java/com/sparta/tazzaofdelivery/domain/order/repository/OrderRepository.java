package com.sparta.tazzaofdelivery.domain.order.repository;

import com.sparta.tazzaofdelivery.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
