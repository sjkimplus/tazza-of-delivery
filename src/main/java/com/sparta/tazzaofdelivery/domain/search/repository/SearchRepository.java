package com.sparta.tazzaofdelivery.domain.search.repository;

import com.sparta.tazzaofdelivery.domain.search.dto.SearchPopularResponse;
import com.sparta.tazzaofdelivery.domain.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search, Long> {
    @Query("SELECT s FROM Search s ORDER BY s.searchCount DESC")
    List<Search> findTop10ByOrderBySearchCountDesc();

    Optional<Search> findByKeyword(String keyword);

    @Modifying
    @Query("UPDATE Search s SET s.searchCount = s.searchCount + 1 WHERE s.keyword = :keyword")
    void incrementSearchCount(@Param("keyword") String keyword);
}
