package com.martinacat.berriesfinder.view;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TotalPrice {
    private final BigDecimal gross;
    private final BigDecimal vat;
}
