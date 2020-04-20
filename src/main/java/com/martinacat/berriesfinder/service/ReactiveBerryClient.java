package com.martinacat.berriesfinder.service;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Provides the Berries, cherries and currants page as a Html Document
 *
 **/
@Service
public class ReactiveBerryClient {

    private final WebClient client;
    private static final Function<String, Document> toHtmlDocument = Jsoup::parse;
    private final String berriesPagePath;


    ReactiveBerryClient(@Value("${berries.page.base-url}") final String url,
                        @Value("${berries.page.resource-path}") final String resourcePath) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
        this.berriesPagePath = resourcePath;
    }

    public Document getHtmlPage() {
        return client
                .get()
                .uri(berriesPagePath)
                .retrieve()
                .bodyToMono(String.class)
                .map(toHtmlDocument).block();
    }

    public List<Document> getProductPages() {
        return Collections.emptyList(); // todo replace stub
    }
}
