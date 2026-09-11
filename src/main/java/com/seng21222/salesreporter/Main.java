package com.seng21222.salesreporter;

import com.seng21222.salesreporter.core.SalesCalculator;
import com.seng21222.salesreporter.core.SalesSummary;
import com.seng21222.salesreporter.exception.InvalidDataException;
import com.seng21222.salesreporter.exception.InvalidOutputMethodException;
import com.seng21222.salesreporter.io.CsvProductReader;
import com.seng21222.salesreporter.io.ProductReader;
import com.seng21222.salesreporter.model.Product;
import com.seng21222.salesreporter.output.ConsoleReportWriter;
import com.seng21222.salesreporter.output.FileReportWriter;
import com.seng21222.salesreporter.output.ReportWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Command-line entry point for the SalesReporter tool.
 *
 * <p>Usage:
 * <pre>java SalesReporter &lt;csv-file-path&gt; &lt;output-method&gt; [output-file-path]</pre>
 *
 * <p>This class is the glue between the three layers built by the rest of the
 * team: it reads and validates the arguments, asks the
 * {@link ProductReader} to load the raw data, hands the data to the
 * {@link SalesCalculator}, and finally passes the summary to whichever
 * {@link ReportWriter} the user asked for.
 *
 * <p>It is also the single place where failures are turned into messages. Every
 * expected problem (wrong number of arguments, unknown output method, missing
 * file, malformed CSV row, unwritable output file) is caught here and reported
 * as one clear line on {@code System.err} with a non-zero exit status, so a
 * stack trace never reaches the user.
 */
public class Main {

    /** Exit status returned to the operating system when the run failed. */
    private static final int EXIT_FAILURE = 1;

    /**
     * Runs the tool and guarantees that no exception escapes to the JVM.
     *
     * @param args raw command-line arguments
     */
    public static void main(String[] args) {
        try {
            run(args);
        } catch (InvalidOutputMethodException e) {
            fail(detailOf(e, "the output arguments are not valid"));
        } catch (InvalidDataException e) {
            fail("The CSV data is invalid - " + detailOf(e, "a row could not be parsed"));
        } catch (FileNotFoundException e) {
            fail(detailOf(e, "the CSV file could not be found"));
        } catch (IOException e) {
            fail("A file could not be read or written - " + detailOf(e, "check the paths you passed"));
        } catch (Exception e) {
            fail("Unexpected error (" + e.getClass().getSimpleName() + ") - "
                    + detailOf(e, "no further detail available"));
        }
    }

    /**
     * Performs one complete read -&gt; calculate -&gt; write cycle.
     *
     * <p>Kept separate from {@link #main(String[])} so that the workflow can be
     * unit tested without having to deal with {@code System.exit}.
     *
     * @param args raw command-line arguments
     * @throws InvalidOutputMethodException if the output method is not recognised
     *                                      or the output file path is missing
     * @throws InvalidDataException         if the CSV holds a row that cannot be parsed
     * @throws IOException                  if the input or output file cannot be accessed
     * @throws Exception                    if the report cannot be written; the {@code output}
     *                                      layer declares a broad checked exception here, so it
     *                                      is propagated and translated by {@link #main(String[])}
     */
    static void run(String[] args) throws Exception {
        if (args.length < 2) {
            printUsage();
            throw new InvalidOutputMethodException(
                    "Expected at least 2 arguments but received " + args.length + " (see the usage above).");
        }

        String csvFilePath = args[0];
        String outputMethod = args[1];

        // Resolve the writer first: rejecting a bad output method is cheaper than
        // parsing a whole file we are never going to report on.
        ReportWriter writer = resolveWriter(outputMethod, args);

        ProductReader reader = new CsvProductReader();
        List<Product> products = reader.readProducts(csvFilePath);

        SalesCalculator calculator = new SalesCalculator();
        SalesSummary summary = calculator.calculate(products);

        writer.write(summary);
    }

    /**
     * Chooses the report destination requested by the user.
     *
     * @param outputMethod the second command-line argument, {@code console} or {@code file}
     * @param args         the full argument array, needed for the optional output path
     * @return a writer that prints the report or saves it to disk
     * @throws InvalidOutputMethodException if the method is unknown, or is {@code file}
     *                                      without a third argument giving the target path
     */
    static ReportWriter resolveWriter(String outputMethod, String[] args)
            throws InvalidOutputMethodException {
        if ("console".equalsIgnoreCase(outputMethod)) {
            return new ConsoleReportWriter();
        }
        if ("file".equalsIgnoreCase(outputMethod)) {
            if (args.length < 3) {
                throw new InvalidOutputMethodException(
                        "An output-file-path is required when the output method is 'file'.");
            }
            return new FileReportWriter(args[2]);
        }
        throw new InvalidOutputMethodException("Invalid output method '" + outputMethod
                + "'. Use 'console' or 'file'.");
    }

    /**
     * Prints the command syntax, argument by argument, to {@code System.err}.
     */
    private static void printUsage() {
        System.err.println("Usage: java SalesReporter <csv-file-path> <output-method> [output-file-path]");
        System.err.println("  <csv-file-path>      file holding the sales rows to report on");
        System.err.println("  <output-method>      'console' to print, 'file' to save the report");
        System.err.println("  [output-file-path]   required only when <output-method> is 'file'");
    }

    /**
     * Describes a failure in one line without ever exposing a stack trace.
     *
     * @param thrown           the exception that reached {@link #main(String[])}
     * @param whenNoMessage    fallback wording for exceptions thrown without a message
     * @return the exception's own message, which the throwing class already wrote
     *         for the user, or the fallback when that message is missing
     */
    private static String detailOf(Exception thrown, String whenNoMessage) {
        String message = thrown.getMessage();
        return (message == null || message.isBlank()) ? whenNoMessage : message;
    }

    /**
     * Reports one handled failure and stops the program.
     *
     * @param message the whole message the user should see
     */
    private static void fail(String message) {
        System.err.println("Error: " + message);
        System.exit(EXIT_FAILURE);
    }
}
