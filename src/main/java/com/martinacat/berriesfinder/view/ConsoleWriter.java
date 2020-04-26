package com.martinacat.berriesfinder.view;

public class ConsoleWriter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private ConsoleWriter() {}

    public static void write(String message) {
        System.out.println(message);
    }

    public static void writeGreen(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public static void warning(String message) {
        System.out.println(ANSI_YELLOW + "[warning] " + message + ANSI_RESET);
    }

    public static void error(String message) {
        System.out.println(ANSI_RED + "[error] " + message + ANSI_RESET);
    }
}
