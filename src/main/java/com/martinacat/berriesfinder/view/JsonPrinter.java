package com.martinacat.berriesfinder.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPrinter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String generateJson(Object object) throws JsonProcessingException {
        return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}
