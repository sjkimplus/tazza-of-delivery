package com.sparta.tazzaofdelivery.domain.store.repository;

import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    long countByUserAndStatus(User user, StoreStatus status);

}
