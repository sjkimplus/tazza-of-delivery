package com.sparta.tazzaofdelivery.domain.search.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@Table(name="Search")
@NoArgsConstructor
public class Search {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchId;

    @Column(name="key_word")
    private String keyword;

    @Column(name="search_count")
    private int searchCount;


    public Search(String keyword, int searchCount) {
        this.keyword = keyword;
        this.searchCount = searchCount;
    }
}
