package com.martinacat.berriesfinder.view;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Listing {
    private final String title;
    private final Double unitPrice;
    private final String kcalPer100g;
    private final String description;
}
