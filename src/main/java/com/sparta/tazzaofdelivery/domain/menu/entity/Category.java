package com.sparta.tazzaofdelivery.domain.menu.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    CHICKEN("치킨"),
    PIZZA("피자"),
    WESTERN("양식"),
    CHINESE("중식"),
    KOREAN("한식"),
    JAPANESE("일식"),
    DESSERT("디저트"),
    COFFEE("커피");

    private final String category;
}
