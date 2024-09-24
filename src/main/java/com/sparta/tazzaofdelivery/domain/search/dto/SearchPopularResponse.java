package com.sparta.tazzaofdelivery.domain.search.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchPopularResponse {
    private final String keyword;
    private final int searchCount;
}
