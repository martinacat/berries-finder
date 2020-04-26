package com.martinacat.berriesfinder.integration;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.martinacat.berriesfinder.TestUtil;
import com.martinacat.berriesfinder.client.ReactiveClient;
import com.martinacat.berriesfinder.service.ProductService;
import com.martinacat.berriesfinder.service.impl.ProductServiceImpl;
import com.martinacat.berriesfinder.view.ConsoleWriter;
import com.martinacat.berriesfinder.view.JsonPrinter;
import com.martinacat.berriesfinder.view.Listing;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import static com.martinacat.berriesfinder.TestUtil.blueberriesUrl;
import static com.martinacat.berriesfinder.TestUtil.strawberriesUrl;
import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceClientIntegrationTest {

    public static MockWebServer mockWebServer;
    private final String resourceUrl = "/testBaseResource";

    @Autowired
    private ProductService productService;

    private final Dispatcher dispatcher = new Dispatcher() {
        @NotNull
        @Override
        public MockResponse dispatch(@NotNull RecordedRequest request) {
            if (resourceUrl.equals(request.getPath())) {
                return new MockResponse().setBody(TestUtil.productsPageHtml);
            } else if (strawberriesUrl.equals(request.getPath())) {
                return new MockResponse().setResponseCode(500);
            } else if (blueberriesUrl.equals(request.getPath())) {
                return new MockResponse().setResponseCode(200).setBody(TestUtil.blueberriesPageHtml);
            }
            return new MockResponse().setResponseCode(404);
        }
    };

    @BeforeAll
    static void setUp() throws IOException {
        TestUtil.init();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void init() {
        mockWebServer.setDispatcher(dispatcher);
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        ReactiveClient client = new ReactiveClient(baseUrl);

        productService = new ProductServiceImpl(client, resourceUrl);
    }

    @AfterAll
    static void tearDown() throws IOException{
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("When ProductService is called, " +
            "then multiple resources are loaded asynchronously and the available ones are retrieved")
    void whenServiceIsCalled_thenAvailableResourcesAreFetched() throws JsonProcessingException {
        List<Listing> listings = productService.getProducts();
        ConsoleWriter.write(JsonPrinter.generateJson(listings));

        Assertions.assertEquals(3, listings.size());
        assertThat(listings, Matchers.containsInAnyOrder(
                Listing.builder()
                        .title("Sainsbury's British Cherry & Strawberry Pack 600g")
                        .unitPrice(4.0)
                        .kcalPer100g(null)
                        .description(null)
                        .build(),
                Listing.builder()
                        .title("Sainsbury's Strawberries 400g")
                        .unitPrice(1.75)
                        .kcalPer100g(null)
                        .description(null)
                        .build(),
                Listing.builder()
                        .title("Sainsbury's Blueberries 200g")
                        .unitPrice(1.75)
                        .kcalPer100g("45kcal")
                        .description("by Sainsbury's blueberries")
                        .build()));
    }
}