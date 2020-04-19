package com.martinacat.berriesfinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUtil {

    private static final String testPageFilePath = "src/test/resources/berries-test-page.html";
    public static String berriesTestPageHtml;
    public static Document berriesHtmlDocument;

    public static void init() {
        readTestPage();
        parseHtmlToDocument();
    }

    private static void readTestPage() {
        try {
            berriesTestPageHtml = new String(Files.readAllBytes(Paths.get(testPageFilePath)));
        } catch (IOException e) {
            fail("Unable to read content of test html file");
        }
    }

    private static void parseHtmlToDocument() {
        berriesHtmlDocument = Jsoup.parse(berriesTestPageHtml);
    }
}
