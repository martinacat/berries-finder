package com.martinacat.berriesfinder.service;

import java.util.Collections;
import java.util.List;

import com.martinacat.berriesfinder.entity.Product;
import org.jsoup.nodes.Document;
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
        Document page = reactiveBerryClient.getHtmlPage();

        return Collections.emptyList();
    }
}
