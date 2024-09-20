package com.sparta.tazzaofdelivery.domain.store.repository;

import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    long countByAuthUserId(Long authUserId);
    long countByAuthOwnerIdAndStatus(Long ownerId, StoreStatus status);
}
