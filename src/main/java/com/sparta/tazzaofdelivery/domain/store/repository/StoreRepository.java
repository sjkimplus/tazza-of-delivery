package com.sparta.tazzaofdelivery.domain.store.repository;

import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    long countByUserAndStatus(User user, StoreStatus status);

    /*
    * 통합검색
    *  DISTINCT : 중복 제거
    *  LEFT JOIN : store 엔티티에 해당하는 메뉴가 없어도 가게 정보 가져올 수 있도록, menu 테이블 조인
    *  LIKE %Name% : 가게 or 메뉴 이름 검색
    *  StoreStatus.ACTIVE 인 가게만 검색할 수 있도록, 필터링
    * */
    @Query("SELECT DISTINCT s FROM Store s " +
            "LEFT JOIN s.menus m " +
            "WHERE (:storeName IS NULL OR s.storeName LIKE %:storeName%) OR " +
            "(:menuName IS NULL OR m.menuName LIKE %:menuName%) OR " +
            "(:status IS NULL OR s.status = :status)")
    List<Store> searchStoresWithMenu(@Param("storeName") String storeName,
                                     @Param("menuName") String menuName,
                                     @Param("status") StoreStatus status);


}
