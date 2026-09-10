package com.seng21222.salesreporter.io;

import com.seng21222.salesreporter.exception.InvalidDataException;
import com.seng21222.salesreporter.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvProductReaderTest {

    private final CsvProductReader reader = new CsvProductReader();
    private Path tempFile;

    @AfterEach
    void cleanup() throws IOException {
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
    }

    private Path writeTempCsv(String content) throws IOException {
        tempFile = Files.createTempFile("sales", ".csv");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write(content);
        }
        return tempFile;
    }

    @Test
    void readsValidCsvAndSkipsHeader() throws Exception {
        Path csv = writeTempCsv(
                "product_id, product_name, category, quantity_sold, unit_price\n" +
                        "P001, Wireless Mouse, Electronics, 12, 25.50\n" +
                        "P002, Notebook, Stationery, 35, 3.75\n"
        );

        List<Product> products = reader.readProducts(csv.toString());

        assertEquals(2, products.size());
        assertEquals("P001", products.get(0).productId());
        assertEquals("Wireless Mouse", products.get(0).productName());
        assertEquals(12, products.get(0).quantitySold());
        assertEquals(25.50, products.get(0).unitPrice(), 0.001);
    }

    @Test
    void throwsFileNotFoundForMissingFile() {
        assertThrows(IOException.class, () ->
                reader.readProducts("this-file-does-not-exist.csv"));
    }

    @Test
    void throwsInvalidDataExceptionForMalformedRow() throws IOException {
        Path csv = writeTempCsv(
                "product_id, product_name, category, quantity_sold, unit_price\n" +
                        "P001, Wireless Mouse, Electronics, 12\n" // missing unit_price column
        );

        assertThrows(InvalidDataException.class, () ->
                reader.readProducts(csv.toString()));
    }

    @Test
    void throwsInvalidDataExceptionForNonNumericValue() throws IOException {
        Path csv = writeTempCsv(
                "product_id, product_name, category, quantity_sold, unit_price\n" +
                        "P001, Wireless Mouse, Electronics, abc, 25.50\n" // "abc" instead of a number
        );

        assertThrows(InvalidDataException.class, () ->
                reader.readProducts(csv.toString()));
    }

    @Test
    void skipsBlankLines() throws Exception {
        Path csv = writeTempCsv(
                "product_id, product_name, category, quantity_sold, unit_price\n" +
                        "P001, Wireless Mouse, Electronics, 12, 25.50\n" +
                        "\n" +
                        "P002, Notebook, Stationery, 35, 3.75\n"
        );

        List<Product> products = reader.readProducts(csv.toString());
        assertEquals(2, products.size());
    }
}