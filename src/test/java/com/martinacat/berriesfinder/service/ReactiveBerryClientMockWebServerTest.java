package com.martinacat.berriesfinder.service;

import java.io.IOException;
import java.util.List;

import com.martinacat.berriesfinder.TestUtil;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.martinacat.berriesfinder.TestUtil.blueberriesPath;
import static com.martinacat.berriesfinder.TestUtil.strawberriesPath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReactiveBerryClientMockWebServerTest {
    public static MockWebServer mockWebServer;

    private ReactiveBerryClient testedInstance;

    @BeforeAll
    static void setUp() throws IOException {
        TestUtil.init();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void init() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        testedInstance = new ReactiveBerryClient(baseUrl, "");
    }

    @AfterAll
    static void tearDown() throws IOException{
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("When server response is 2xx, html document is read correctly")
    void whenResponseIs2xx_andHtmlValid_thenDocumentContainsProducts() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody("berriesTestPageHtml"));

        final Document htmlPage = testedInstance.getHtmlPage();

        assertEquals("berriesTestPageHtml", htmlPage.text());
    }

    @Test
    @DisplayName("When server response is 5xx, exception is thrown")
    void whenResponseIs5xx() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        assertThrows(WebClientResponseException.InternalServerError.class, testedInstance::getHtmlPage);
    }

    @Test
    @DisplayName("When server response is 4xx, exception is thrown")
    void whenResponseIs4xx() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.NOT_FOUND.value()));

        assertThrows(WebClientResponseException.NotFound.class, testedInstance::getHtmlPage);
    }

    @Nested
    @DisplayName("Asynchronous requests for each of the product sub-pages.")
    class ProductSubPageRequestTest {

        final Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest request) {
                if (strawberriesPath.equals(request.getPath())) {
                    return new MockResponse().setBody("strawberries");
                } else if (blueberriesPath.equals(request.getPath())) {
                    return new MockResponse().setBody("blueberries");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        @BeforeEach
        void init() {
            mockWebServer.setDispatcher(dispatcher);
        }

//        @Test
//        @DisplayName("When both urls are 2xx, both product child pages are fetched")
        void whenAsyncResponsesAre2xx() {
            List<Document> actualPages = testedInstance.getProductPages();

            assertFalse(actualPages.isEmpty());
            assertEquals("strawberries", actualPages.get(0).text());
            assertEquals("blueberries", actualPages.get(0).text());
        }
    }
}