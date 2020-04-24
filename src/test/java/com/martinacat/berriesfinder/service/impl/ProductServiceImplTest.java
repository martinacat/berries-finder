package com.martinacat.berriesfinder.service.impl;

import java.util.Collections;
import java.util.List;

import com.martinacat.berriesfinder.TestUtil;
import com.martinacat.berriesfinder.client.ReactiveClient;
import com.martinacat.berriesfinder.view.Listing;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Page Scraping Service tests")
class ProductServiceImplTest {

    @Mock
    private final ReactiveClient client = mock(ReactiveClient.class);

    @InjectMocks
    private ProductServiceImpl testedInstance;


    @BeforeAll
    void beforeAll() {
        TestUtil.init();
    }

    @Test
    @DisplayName("given empty html document, when the service is called, then no products are returned")
    void givenEmptyDocument_whenServiceCalled_thenReturnsEmptyList() {
        when(client.getHtmlAsDocumentMono(any())).thenReturn(Mono.just(new Document("")));

        List<Listing> actual = testedInstance.getProducts();

        assertEquals(Collections.EMPTY_LIST, actual);
    }
}