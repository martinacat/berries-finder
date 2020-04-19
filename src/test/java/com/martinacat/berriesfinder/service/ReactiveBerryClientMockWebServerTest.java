package com.martinacat.berriesfinder.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith({SpringExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReactiveBerryClientMockWebServerTest {
    public static MockWebServer mockWebServer;

    private ReactiveBerryClient testedInstance;

    private static final String testPageFilePath = "src/test/resources/berries-test-page.html";
    private static String berriesTestPageHtml;

    @BeforeAll
    static void setUp() throws IOException {
        readTestPage();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void init() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        testedInstance = new ReactiveBerryClient(baseUrl);
    }

    @AfterAll
    static void tearDown() throws IOException{
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("When server response is 2xx, and body is valid html, html document is read correctly")
    void whenResponseIs2xx_andHtmlValid_thenDocumentContainsProducts() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody(berriesTestPageHtml));

        final Document htmlPage = testedInstance.getHtmlPage();
        Elements products = htmlPage.getElementsByClass("product");

        assertEquals(16, products.size());
        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    @DisplayName("When server response is 2xx, and body is invalid html, exception is thrown")
    void whenResponseIs2xx_andBodyIsInvalid_thenExceptionIsThrown() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.OK.value())
                .setBody("<head><"));
        // todo
    }

    @Test
    @DisplayName("When server response is 5xx, exception is thrown")
    void whenResponseIs5xx() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        assertThrows(WebClientResponseException.InternalServerError.class, testedInstance::getHtmlPage);
        assertEquals(1, mockWebServer.getRequestCount());
    }

    @Test
    @DisplayName("When server response is 4xx, exception is thrown")
    void whenResponseIs4xx() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.NOT_FOUND.value()));

        assertThrows(WebClientResponseException.NotFound.class, testedInstance::getHtmlPage);
        assertEquals(1, mockWebServer.getRequestCount());
    }

    private static void readTestPage() {
        try {
            berriesTestPageHtml = new String(Files.readAllBytes(Paths.get(testPageFilePath)));
        } catch (IOException e) {
            fail("Unable to read content of test html file");
        }
    }
}