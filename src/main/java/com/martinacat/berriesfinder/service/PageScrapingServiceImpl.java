package com.martinacat.berriesfinder.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.martinacat.berriesfinder.entity.Product;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PageScrapingServiceImpl implements PageScrapingService {

    private final BerriesClient berriesClient;

    @Autowired
    public PageScrapingServiceImpl(BerriesClient berriesClient) {
        this.berriesClient = berriesClient;
    }

    @Override
    public List<Product> getProducts() {
        String page = berriesClient.getHtmlPage();

        return Collections.emptyList();
    }
}
