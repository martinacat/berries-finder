package com.martinacat.berriesfinder.client;

import java.util.function.Function;

import com.martinacat.berriesfinder.view.ConsoleWriter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Provides the Berries, cherries and currants page as a Html Document
 *
 **/
@Slf4j
@Service
public class ReactiveClient {

    private final WebClient client;
    private static final Function<String, Document> toHtmlDocument = Jsoup::parse;


    public ReactiveClient(@Value("${page.base-url}") final String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<Document> getHtmlAsDocumentMono(String resourceUri) {
        return client
                .get()
                .uri(resourceUri)
                .retrieve()
                .bodyToMono(String.class)
                .map(toHtmlDocument)
                .onErrorResume(throwable -> {
                    // todo maybe implement retries?
                    ConsoleWriter.error(String.format("Unable to retrieve resource: %s, returning empty placeholder. Exception was: %s", resourceUri, throwable.getMessage()));
                    return Mono.empty();
                });
    }
}
