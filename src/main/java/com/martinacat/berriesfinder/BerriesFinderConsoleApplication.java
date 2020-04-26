package com.martinacat.berriesfinder;

import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.martinacat.berriesfinder.service.ProductService;
import com.martinacat.berriesfinder.service.TotalCalculationService;
import com.martinacat.berriesfinder.view.ConsoleWriter;
import com.martinacat.berriesfinder.view.JsonPrinter;
import com.martinacat.berriesfinder.view.JsonView;
import com.martinacat.berriesfinder.view.Listing;
import com.martinacat.berriesfinder.view.TotalPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class BerriesFinderConsoleApplication implements CommandLineRunner {

	private final Scanner scanner = new Scanner(System.in);

	private final ProductService productService;
	private final TotalCalculationService totalCalculationService;

	private final static String WELCOME_MESSAGE = "\nHi. Would you like to scrape the products on the Berries, Cherries, Currants page? y/n";


	public static void main(String[] args) {
		log.info("Starting BerriesFinder...");
		SpringApplication.run(BerriesFinderConsoleApplication.class, args);
		log.info("Application terminated!");
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
		ConsoleWriter.writeGreen(WELCOME_MESSAGE);
		if (scanner.hasNext()) {
			if (scanner.next().equals("y")) {
				List<Listing> products = productService.getProducts();
				TotalPrice total = totalCalculationService.calculateTotal(products);

				ConsoleWriter.write(JsonPrinter.generateJson(new JsonView(products, total)));
				ConsoleWriter.writeGreen("All done! Bye!");
			} else {
				ConsoleWriter.writeGreen("Bye!");
			}
			System.exit(0);
		}
	}
}