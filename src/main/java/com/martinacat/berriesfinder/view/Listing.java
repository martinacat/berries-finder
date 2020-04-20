package com.martinacat.berriesfinder.view;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Listing {
    private final String title;
    private final BigDecimal unitPrice;
    private final Long kcalPer100g;
    private final String description;
}
