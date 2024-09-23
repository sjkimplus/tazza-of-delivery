package com.sparta.tazzaofdelivery.domain.search.service;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.search.dto.SearchPopularResponse;
import com.sparta.tazzaofdelivery.domain.search.entity.Search;
import com.sparta.tazzaofdelivery.domain.search.repository.SearchRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final SearchRepository searchRepository;

    public void recordSearchKeyword(String keyword){
        Optional<Search> existingKeyword = searchRepository.findByKeyword(keyword);
        if(existingKeyword.isPresent()){
            searchRepository.incrementSearchCount(keyword);
        } else{
            Search newKeyword = new Search(keyword,1);
            searchRepository.save(newKeyword);
        }
    }

    @Transactional
    public List<SearchPopularResponse> getTopSearchKeywords() {
        List<Search> searches = searchRepository.findTop10ByOrderBySearchCountDesc();
        List<SearchPopularResponse> responses = new ArrayList<>();
        for (Search search : searches) {
            SearchPopularResponse response = new SearchPopularResponse(
                    search.getKeyword(),
                    search.getSearchCount()
            );
            responses.add(response);
        }
        return responses;
    }
}
