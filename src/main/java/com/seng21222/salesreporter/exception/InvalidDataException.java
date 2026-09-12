package com.seng21222.salesreporter.exception;

/**
 * Thrown when a CSV row cannot be turned into a {@code Product}: the row has too
 * few columns, a quantity or price is not a number, or the file holds no data at
 * all. Reported by the {@code io} layer and reported to the user by
 * {@link com.seng21222.salesreporter.Main}.
 */
public class InvalidDataException extends Exception {

    /**
     * @param message human-readable description, usually including the offending row
     */
    public InvalidDataException(String message) {
        super(message);
    }
}