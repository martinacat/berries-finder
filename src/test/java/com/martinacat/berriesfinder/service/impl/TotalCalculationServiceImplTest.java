package com.martinacat.berriesfinder.service.impl;

import java.util.Arrays;
import java.util.List;

import com.martinacat.berriesfinder.service.TotalCalculationService;
import com.martinacat.berriesfinder.view.Listing;
import com.martinacat.berriesfinder.view.TotalPrice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TotalCalculationServiceImplTest {

    private static TotalCalculationService testedInstance;

    @BeforeAll
    static void beforeAll() {
        testedInstance = new TotalCalculationServiceImpl();
    }

    @Test
    @DisplayName("Total and gross are calculated correctly")
    void testTotal() {
        TotalPrice actual = testedInstance.calculateTotal(getListings());

        Assertions.assertEquals(5.5, actual.getGross());
        Assertions.assertEquals(1.1, actual.getVat());
    }

    private List<Listing> getListings() {
        return Arrays.asList(
                Listing.builder()
                        .title("Sainsbury's Strawberries 400g")
                        .unitPrice(1.75)
                        .description("by Sainsbury's strawberries")
                        .kcalPer100g("33kcal")
                        .build(),
                Listing.builder()
                        .title("Sainsbury's Blueberries 200g")
                        .unitPrice(3.75)
                        .description("by Sainsbury's blueberries")
                        .kcalPer100g("45kcal")
                        .build()
        );
    }
}