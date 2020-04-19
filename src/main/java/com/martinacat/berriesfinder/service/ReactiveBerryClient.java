package com.martinacat.berriesfinder.service;

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
    private static final String BERRIES_PAGE_PATH = "/berries-cherries-currants6039.html";


    ReactiveBerryClient(@Value("${berries.page.base-url}") final String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Document getHtmlPage() {
        return client
                .get()
                .uri(BERRIES_PAGE_PATH)
                .retrieve()
                .bodyToMono(String.class)
                .map(toHtmlDocument).block();
    }
}
