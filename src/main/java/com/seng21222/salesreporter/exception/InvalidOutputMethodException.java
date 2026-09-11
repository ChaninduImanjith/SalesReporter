package com.seng21222.salesreporter.exception;

/**
 * Thrown when the user supplies an output method on the command line that the
 * tool does not understand (anything other than {@code console} or {@code file}),
 * or when the optional output file path is missing while writing to a file.
 *
 * <p>This is a checked exception so that {@link com.seng21222.salesreporter.Main}
 * is forced to handle it and turn it into a friendly message instead of letting
 * an unchecked failure escape to the user.
 *
 * <h3>Examples</h3>
 * <ul>
 *   <li>{@code java SalesReporter sales.csv badmethod} → throws with message
 *       "Invalid output method 'badmethod'. Use 'console' or 'file'."</li>
 *   <li>{@code java SalesReporter sales.csv file} → throws with message
 *       "An output-file-path is required when the output method is 'file'."</li>
 * </ul>
 *
 * @see com.seng21222.salesreporter.Main#resolveWriter(String, String[])
 */
public class InvalidOutputMethodException extends Exception {

    /**
     * @param message human-readable explanation of what the user typed wrongly
     */
    public InvalidOutputMethodException(String message) {
        super(message);
    }
}
