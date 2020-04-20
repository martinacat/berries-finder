package com.martinacat.berriesfinder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.martinacat.berriesfinder.view.ConsoleWriter;
import com.martinacat.berriesfinder.view.DefaultJsonView;
import com.martinacat.berriesfinder.view.JsonPrinter;
import com.martinacat.berriesfinder.view.TotalPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BerriesFinderConsoleApplication implements CommandLineRunner {

	private final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		log.info("Starting BerriesFinder...");
		SpringApplication.run(BerriesFinderConsoleApplication.class, args);
		log.info("Application terminated!");
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
		log.info("Hi! Executing command line tool BerriesFinder.");

		// temporary empty object
		DefaultJsonView defaultJsonView =
				new DefaultJsonView(
						Collections.emptyList(),
						new TotalPrice(BigDecimal.valueOf(1),BigDecimal.valueOf(1))
				);

		ConsoleWriter.write(JsonPrinter.generateJson(defaultJsonView));
	}
}