package com.martinacat.berriesfinder.view;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Listing {
    private final String title;
    private final Double unitPrice;
    private final Long kcalPer100g;
    private final String description;
}