package com.martinacat.berriesfinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUtil {

    private static final String productsPageFilePath = "src/test/resources/berries-test-page.html";
    private static final String blueberriesPageFilePath = "src/test/resources/blueberries.html";
    public static final String strawberriesUrl = "/shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html";
    public static final String blueberriesUrl = "/shop/gb/groceries/berries-cherries-currants/sainsburys-blueberries-200g.html";

    public static String productsPageHtml;
    public static Document productsPageDocument;

    public static String blueberriesPageHtml;

    public static void init() {
        readTestPage();
        parseHtmlToDocument();
    }

    private static void readTestPage() {
        try {
            productsPageHtml = new String(Files.readAllBytes(Paths.get(productsPageFilePath)));
            blueberriesPageHtml = new String(Files.readAllBytes(Paths.get(blueberriesPageFilePath)));
        } catch (IOException e) {
            fail("Unable to read content of test html file");
        }
    }

    private static void parseHtmlToDocument() {
        productsPageDocument = Jsoup.parse(productsPageHtml);
    }
}
