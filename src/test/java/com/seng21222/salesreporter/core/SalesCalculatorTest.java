package com.seng21222.salesreporter.core;

import com.seng21222.salesreporter.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalesCalculatorTest {

    private final SalesCalculator calculator = new SalesCalculator();

    @Test
    void calculatesRevenuePerProductAndGrandTotal() {
        List<Product> products = List.of(
                new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
                new Product("P002", "Notebook", "Stationery", 35, 3.75)
        );

        SalesSummary summary = calculator.calculate(products);

        assertEquals(306.00, products.get(0).getRevenue(), 0.001);
        assertEquals(131.25, products.get(1).getRevenue(), 0.001);
        assertEquals(437.25, summary.getGrandTotalRevenue(), 0.001);
    }

    @Test
    void calculatesRevenuePerCategory() {
        List<Product> products = List.of(
                new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
                new Product("P003", "USB Hub", "Electronics", 8, 18.00),
                new Product("P002", "Notebook", "Stationery", 35, 3.75)
        );

        SalesSummary summary = calculator.calculate(products);

        assertEquals(450.00, summary.getRevenuePerCategory().get("Electronics"), 0.001);
        assertEquals(131.25, summary.getRevenuePerCategory().get("Stationery"), 0.001);
    }

    @Test
    void findsBestSellingProductByQuantity() {
        List<Product> products = List.of(
                new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
                new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50)
        );

        SalesSummary summary = calculator.calculate(products);

        assertEquals("P004", summary.getBestSellingProduct().productId());
        assertEquals(100, summary.getBestSellingProduct().quantitySold());
    }

    @Test
    void findsHighestRevenueProductEvenIfNotBestSeller() {
        // Ballpoint Pen sells the most units, but Wireless Mouse earns more revenue
        List<Product> products = List.of(
                new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
                new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50)
        );

        SalesSummary summary = calculator.calculate(products);

        assertEquals("P001", summary.getHighestRevenueProduct().productId());
        assertEquals(306.00, summary.getHighestRevenueProduct().getRevenue(), 0.001);
    }

    @Test
    void throwsExceptionForEmptyProductList() {
        assertThrows(IllegalArgumentException.class, () ->
                calculator.calculate(List.of()));
    }
}