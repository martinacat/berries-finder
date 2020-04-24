package com.martinacat.berriesfinder.client;

import java.io.IOException;

import com.martinacat.berriesfinder.TestUtil;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReactiveClientTest {

    public static MockWebServer mockWebServer;

    private ReactiveClient testedInstance;

    private static final String resourceUri = "/resource";

    @BeforeAll
    static void setUp() throws IOException {
        TestUtil.init();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void init() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        testedInstance = new ReactiveClient(baseUrl);
    }

    @AfterAll
    static void tearDown() throws IOException{
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("When server response is 2xx, html is read returned as document")
    void whenResponseIs2xx_thenBodyIsReturnedAsDocument() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody("berriesTestPageHtml"));

        final Document htmlPage = testedInstance.getHtmlAsDocumentMono(resourceUri).block();

        assertNotNull(htmlPage);
        assertEquals("berriesTestPageHtml", htmlPage.text());
    }

    @Test
    @DisplayName("When server response is 5xx, exception is thrown")
    void whenResponseIs5xx() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        testedInstance.getHtmlAsDocumentMono(resourceUri);
        //assertEquals(Mono.empty(), actual);
    }

    @Test
    @DisplayName("When server response is 4xx, exception is thrown")
    void whenResponseIs4xx() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.NOT_FOUND.value()));
    }
}