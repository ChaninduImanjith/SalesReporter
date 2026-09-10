package com.seng21222.salesreporter.io;

import com.seng21222.salesreporter.exception.InvalidDataException;
import com.seng21222.salesreporter.model.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvProductReader implements ProductReader {

    @Override
    public List<Product> readProducts(String filePath) throws IOException, InvalidDataException {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header row
                }
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    throw new InvalidDataException("Malformed row (expected 5 columns): " + line);
                }

                try {
                    products.add(new Product(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim()),
                            Double.parseDouble(parts[4].trim())
                    ));
                } catch (NumberFormatException e) {
                    throw new InvalidDataException("Invalid numeric value in row: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("CSV file not found: " + filePath);
        }

        if (products.isEmpty()) {
            throw new InvalidDataException("CSV file contains no product data: " + filePath);
        }

        return products;
    }
}