package com.martinacat.berriesfinder.entity;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Product {
    private final String productResourcePath;
    private final String title;
    private final BigDecimal unitPriceGbp;
    private final Long kcalPer100g;
    private final String description;
}
