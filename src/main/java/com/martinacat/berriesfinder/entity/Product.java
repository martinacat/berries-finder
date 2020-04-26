package com.martinacat.berriesfinder.entity;

import com.martinacat.berriesfinder.view.Listing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String productResourcePath;
    private String title;
    private Double unitPriceGbp;
    private String description;
    private Long kcalPer100g;

    public Listing toListing() {
        return Listing.builder()
                .title(getTitle())
                .unitPrice(getUnitPriceGbp())
                .kcalPer100g(getKcalPer100g())
                .description(getDescription())
                .build();
    }
}
