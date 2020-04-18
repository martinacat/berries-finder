package com.martinacat.berriesfinder.service;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
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

@ExtendWith({SpringExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BerriesClientMockWebServerTest {
    public static MockWebServer mockWebServer;

    private BerriesClient testedInstance;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void init() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        testedInstance = new BerriesClient(baseUrl);
    }

    @AfterAll
    static void tearDown() throws IOException{
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("wip mockWebServer")
    void calledOnce() {
        String expected = "hey hey";
        mockWebServer.enqueue(new MockResponse()
        .setResponseCode(HttpStatus.OK.value())
        .setBody(expected));

        String actual = testedInstance.getHtmlPage();

        assertEquals(expected, actual);
        assertEquals(1, mockWebServer.getRequestCount());
    }
}