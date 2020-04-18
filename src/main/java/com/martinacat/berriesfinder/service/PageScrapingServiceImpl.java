package com.martinacat.berriesfinder.service;

import java.util.Collections;
import java.util.List;

import com.martinacat.berriesfinder.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PageScrapingServiceImpl implements PageScrapingService {

    private final String url;
    private final BerriesClient berriesClient;

    @Autowired
    public PageScrapingServiceImpl(@Value("${berries.page.url}") final String url, BerriesClient berriesClient) {
        this.url = url;
        this.berriesClient = berriesClient;
    }

    @Override
    public List<Product> getProducts() {
        String page = berriesClient.getHtmlPage();

        return Collections.emptyList();
    }
}
