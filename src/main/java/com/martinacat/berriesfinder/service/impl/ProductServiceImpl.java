package com.martinacat.berriesfinder.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.martinacat.berriesfinder.client.ReactiveClient;
import com.martinacat.berriesfinder.entity.Product;
import com.martinacat.berriesfinder.service.ProductService;
import com.martinacat.berriesfinder.view.ConsoleWriter;
import com.martinacat.berriesfinder.view.Listing;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ProductServiceImpl implements ProductService {

    private final ReactiveClient reactiveClient;
    private final String resourcePath;

    @Autowired
    public ProductServiceImpl(ReactiveClient reactiveClient,
                              @Value("${berries.page.resource-path}") final String resourcePath) {
        this.reactiveClient = reactiveClient;
        this.resourcePath = resourcePath;
    }

    @Override
    public List<Listing> getProducts() {
        Elements products = reactiveClient.getHtmlAsDocumentMono(resourcePath)
                .map(removeCrossSellProducts())
                .block();

        return populateProductFields(Objects.requireNonNull(products));
    }

    private Function<Document, Elements> removeCrossSellProducts() {
        return document -> {
            Elements products = document.getElementsByClass("product");
            Elements crossSellProducts = document.getElementsByClass("hasCrossSell");
            products.removeAll(crossSellProducts);

            return products;
        };
    }

    private List<Listing> populateProductFields(Elements elements) {

        if (elements.isEmpty()) {
            ConsoleWriter.error("No products found");
            return Collections.emptyList();
        }

        List<Product> productList = mapElementsToProducts(elements);

        Iterator<Product> productIterator = fetchProductChildPages(productList).iterator();
        List<Listing> finishedListings = new ArrayList<>();

        while (productIterator.hasNext()) {
            finishedListings.add(productIterator.next().toListing());
        }

        return finishedListings;
    }

    // asynchronous fetching of the product pages
    private Iterable<Product> fetchProductChildPages(List<Product> products) {
        return Flux.fromIterable(products)
                .parallel()
                .runOn(Schedulers.elastic())
                .flatMap(fillProductDetails())
                .sequential()
                .toIterable();
    }

    private Function<Product, Publisher<? extends Product>> fillProductDetails() {
        return product -> {
            Mono<Document> d = reactiveClient.getHtmlAsDocumentMono(product.getProductResourcePath());
            if (Objects.nonNull(d.block())) {
                    product.setKcalPer100g(getKcalPer100gFromProductPage(d.block()));
                    product.setDescription(getProductDescription(d.block()));
            }
            return Mono.just(product);
        };
    }

    private List<Product> mapElementsToProducts(Elements elements) {
        return elements
                .stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private Product toProduct(Element element) {
        return Product.builder()
                .title(getProductTitle(element))
                .unitPriceGbp(getProductUnitPrice(element))
                .productResourcePath(getProductPageUrl(element))
                .build();
    }

    private String getProductTitle(Element element) {
        return element.getElementsByClass("productNameAndPromotions").text();
    }

    private Double getProductUnitPrice(Element element) {
        String price = element.getElementsByClass("pricePerUnit")
                .first().childNode(0).toString()
                .replaceAll("[£ ]", "");

        return Double.parseDouble(price);
    }

    private String getProductPageUrl(Element element) {
        return element.getElementsByClass("productNameAndPromotions")
                .first().getElementsByAttribute("href")
                .first().attributes().get("href").substring(17);
    }

    private String getProductDescription(Document document) {
        return document.getElementsByClass("productText").get(0).text()
                .replace("Description ", "");
    }

    private Long getKcalPer100gFromProductPage(Document document) {
        try {
            return Long.valueOf(document.getElementsByClass("nutritionTable")
                    .first()
                    .getElementsByClass("tableRow0").get(0)
                    .getElementsContainingText("kcal").get(1).text()
                    .replace("kcal", ""));
        } catch (NullPointerException | NumberFormatException e) {
            ConsoleWriter.warning(String.format("kcal value not found for %s", document.select("title").get(0).text()));
            return null;
        }
    }
}
