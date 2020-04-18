package com.martinacat.berriesfinder.entity;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {
    private final String productPageUrl;
    private final String title;
    private final BigDecimal unitPrice;
    private final Long kcalPer100g;
    private final String description;
}
