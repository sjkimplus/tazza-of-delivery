package com.sparta.tazzaofdelivery.domain.search.controller;

import com.sparta.tazzaofdelivery.domain.search.service.SearchService;
import com.sparta.tazzaofdelivery.domain.search.dto.SearchPopularResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;


    @GetMapping("/popular")
    public ResponseEntity<List<SearchPopularResponse>> getPopularKeywords() {
        List<SearchPopularResponse> response = searchService.getTopSearchKeywords();
        return ResponseEntity.ok(response);
    }

}
