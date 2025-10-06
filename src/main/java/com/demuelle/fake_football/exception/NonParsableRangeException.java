package com.demuelle.fake_football.exception;

public class NonParsableRangeException extends RuntimeException {
    public NonParsableRangeException() {
        super();
    }

    public NonParsableRangeException(String value) {
        super("Failed to parse \"" + value + "\": " +  "Must be of format x-y where x and y are positive integers, and x is less than y.");
    }
}
