package com.martinacat.berriesfinder.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BerriesClient {

    private final WebClient client;

    BerriesClient(@Value("${berries.page.base-url}") final String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public String getHtmlPage() {
        String resource = "/berries-cherries-currants6039.html";
        return Objects.requireNonNull(client
                .get()
                .uri(resource)
                .exchange()
                .block())
                .bodyToMono(String.class)
                .block(); // todo clean up
    }
}
