package com.martinacat.berriesfinder.view;

public class ConsoleWriter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private ConsoleWriter() {};

    public static void write(String message) {
        System.out.println(message);
    }

    public static void error(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }
}
