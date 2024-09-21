package com.sparta.tazzaofdelivery.domain.menu.repository;

import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
