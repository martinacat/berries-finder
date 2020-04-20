package com.martinacat.berriesfinder.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.martinacat.berriesfinder.entity.Product;
import com.martinacat.berriesfinder.view.ConsoleWriter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageScrapingServiceImpl implements PageScrapingService {

    private final ReactiveBerryClient reactiveBerryClient;

    @Autowired
    public PageScrapingServiceImpl(ReactiveBerryClient reactiveBerryClient) {
        this.reactiveBerryClient = reactiveBerryClient;
    }

    @Override
    public List<Product> getProducts() {

        Elements htmlProducts = getHtmlProductList();

        if (htmlProducts.isEmpty()) {
            ConsoleWriter.error("No products found");

            return Collections.emptyList();
        }

        return populateProductFields(htmlProducts);
    }

    private List<Product> populateProductFields(Elements elements) {
        return elements
                .stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private Elements getHtmlProductList() {
        Document page = reactiveBerryClient.getHtmlPage();
        Elements products = page.getElementsByClass("product");

        return removeCrossSellProducts(page, products);
    }

    private Elements removeCrossSellProducts(Document page, Elements products) {
        Elements crossSell = page.getElementsByClass("hasCrossSell");
        products.removeAll(crossSell);

        return products;
    }

    private Product toProduct(Element element) {
        return Product.builder()
                .title(getProductTitle(element))
                .description(getProductDescription(element))
                .unitPriceGbp(getProductUnitPrice(element))
                .productResourcePath(getProductPageUrl(element))
                .build();
    }

    private String getProductTitle(Element element) {
        return "";
    }

    private String getProductDescription(Element element) {
        return "";
    }

    private BigDecimal getProductUnitPrice(Element element) {
        String price = element.getElementsByClass("pricePerUnit")
                .first().childNode(0).toString()
                .replaceAll("[£ ]", "");

        return new BigDecimal(price);
    }

    private String getProductPageUrl(Element element) {
        return element.getElementsByClass("productNameAndPromotions")
                .first().getElementsByAttribute("href")
                .first().attributes().get("href");
    }
}
