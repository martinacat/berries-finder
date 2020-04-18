package com.martinacat.berriesfinder;

import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BerriesFinderApplication implements CommandLineRunner {

	private final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		log.info("Starting BerriesFinder...");
		SpringApplication.run(BerriesFinderApplication.class, args);
		log.info("Application terminated!");
	}

	@Override
	public void run(String... args) {
		log.info("Hi! Executing command line tool BerriesFinder.");
		System.out.println("Would you like to scrape some berries? y/n");
		if (scanner.hasNext() && "y".equals(scanner.nextLine())) {
			System.out.println("Okay!");
		} else {
			System.out.println("Bye!");
		}
	}
}