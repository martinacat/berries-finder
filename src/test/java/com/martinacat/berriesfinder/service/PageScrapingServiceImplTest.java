package com.martinacat.berriesfinder.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.martinacat.berriesfinder.TestUtil;
import com.martinacat.berriesfinder.entity.Product;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PageScrapingServiceImplTest {

    private final ReactiveBerryClient reactiveBerryClient = mock(ReactiveBerryClient.class);

    private PageScrapingServiceImpl testedInstance;


    @BeforeAll
    void beforeAll() {
        TestUtil.init();
        testedInstance = new PageScrapingServiceImpl(reactiveBerryClient);
    }

    @Test
    @DisplayName("given empty html document, when the service is called, then no products are returned")
    void givenEmptyDocument_whenServiceCalled_thenReturnsEmptyList() {
        when(reactiveBerryClient.getHtmlPage()).thenReturn(new Document(""));

        List<Product> actual = testedInstance.getProducts();

        assertEquals(Collections.EMPTY_LIST, actual);
    }

    @Test
    @DisplayName("given original html document, " +
            "when the service is called, " +
            "then all non crossSell products are returned")
    void givenOriginalDocument_whenServiceCalled_thenReturnsAllNonCrossSellProducts() {
        when(reactiveBerryClient.getHtmlPage()).thenReturn(TestUtil.berriesHtmlDocument);

        List<Product> actual = testedInstance.getProducts();

        assertEquals(14, actual.size());
    }

//    @Test
    @DisplayName("given original html document, " +
            "when the service is called, " +
            "then kcal field is populated")
    void givenOriginalDocument_whenServiceCalled_thenKcalFieldIsPopulated() {
        when(reactiveBerryClient.getHtmlPage()).thenReturn(TestUtil.berriesHtmlDocument);

        List<Product> actual = testedInstance.getProducts();

        assertEquals(getExpectedProducts().get(0).getKcalPer100g(), actual.get(0).getKcalPer100g());
        assertEquals(getExpectedProducts().get(1).getKcalPer100g(), actual.get(1).getKcalPer100g());
    }

    private List<Product> getExpectedProducts() {
        return Arrays.asList(
                Product.builder()
                        .title("Sainsbury's Strawberries 400g")
                        .unitPriceGbp(BigDecimal.valueOf(1.75))
                        .description("by Sainsbury's strawberries")
                        .kcalPer100g(33L)
                        .productResourcePath("/shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html")
                        .build(),
                Product.builder()
                        .title("Sainsbury's Blueberries 200g")
                        .unitPriceGbp(BigDecimal.valueOf(1.75))
                        .description("by Sainsbury's blueberries")
                        .kcalPer100g(45L)
                        .productResourcePath("")
                        .build()
        );
    }
}