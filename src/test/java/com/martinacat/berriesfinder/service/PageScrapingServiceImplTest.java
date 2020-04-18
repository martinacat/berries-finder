package com.martinacat.berriesfinder.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import com.martinacat.berriesfinder.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PageScrapingServiceImplTest {

    @InjectMocks
    private PageScrapingServiceImpl testedInstance;

    @Mock
    private BerriesClient berriesClient;

    private final String testPageFilePath = "src/test/resources/berries-test-page.html";
    private String berriesTestPageHtml;

    @BeforeAll
    void beforeAll() {
        try {
            berriesTestPageHtml = new String(Files.readAllBytes(Paths.get(testPageFilePath)));
        } catch (IOException e) {
            fail("Unable to read content of test html file");
        }
    }

    @BeforeEach
    void setUp() {
//        when(berriesClient.getHtmlPage()).thenReturn(berriesTestPageHtml);
    }

    @Test
    @DisplayName("given empty page, when the service is called, then no products are returned")
    void givenEmptyPage_whenServiceIsCalled_thenNoProductsAreReturned() {
        //when(berriesClient.getHtmlPage()).thenReturn("");

        List<Product> actual = testedInstance.getProducts();

        assertEquals(Collections.EMPTY_LIST, actual);
    }
}