package com.martinacat.berriesfinder.exception;

import com.martinacat.berriesfinder.view.ConsoleWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.InternalServerError.class)
    protected void handle5xx() {
        ConsoleWriter.error("");
    }
}
