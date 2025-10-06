package com.demuelle.fake_football.exception;

public class NonParsableIntegerListException extends RuntimeException {
    public NonParsableIntegerListException() {
        super();
    }

    public NonParsableIntegerListException(String value) {
        super("Failed to parse \"" + value + "\": " +  "Must be of format a,b,c where a, b, and c are positive integers. The list may be of any length.");
    }
}
