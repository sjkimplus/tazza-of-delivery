package com.sparta.tazzaofdelivery.domain.favorite.controller;


import com.sparta.tazzaofdelivery.domain.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class FavoriteController {

    private final FavoriteService favoriteService;
}
