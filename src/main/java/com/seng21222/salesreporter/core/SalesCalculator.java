package com.seng21222.salesreporter.core;

import com.seng21222.salesreporter.model.Product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Computes the revenue and highlight figures shown in the sales report.
 */
public class SalesCalculator {

    public SalesSummary calculate(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be empty");
        }

        Map<String, Double> revenuePerCategory = new LinkedHashMap<>();
        double grandTotal = 0;
        Product bestSeller = products.get(0);
        Product highestRevenue = products.get(0);

        for (Product product : products) {
            double revenue = product.getRevenue();
            grandTotal += revenue;

            revenuePerCategory.merge(product.category(), revenue, Double::sum);

            if (product.quantitySold() > bestSeller.quantitySold()) {
                bestSeller = product;
            }
            if (revenue > highestRevenue.getRevenue()) {
                highestRevenue = product;
            }
        }

        return new SalesSummary(products, revenuePerCategory, bestSeller, highestRevenue, grandTotal);
    }
}
