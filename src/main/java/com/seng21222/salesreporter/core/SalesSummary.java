package com.seng21222.salesreporter.core;

import com.seng21222.salesreporter.model.Product;

import java.util.List;
import java.util.Map;

/**
 * Holds the results computed by {@link SalesCalculator}. This class only stores
 * data - it does not perform any calculation itself (Single Responsibility).
 */
public class SalesSummary {

    private final List<Product> products;
    private final Map<String, Double> revenuePerCategory;
    private final Product bestSellingProduct;
    private final Product highestRevenueProduct;
    private final double grandTotalRevenue;

    public SalesSummary(List<Product> products,
                         Map<String, Double> revenuePerCategory,
                         Product bestSellingProduct,
                         Product highestRevenueProduct,
                         double grandTotalRevenue) {
        this.products = products;
        this.revenuePerCategory = revenuePerCategory;
        this.bestSellingProduct = bestSellingProduct;
        this.highestRevenueProduct = highestRevenueProduct;
        this.grandTotalRevenue = grandTotalRevenue;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<String, Double> getRevenuePerCategory() {
        return revenuePerCategory;
    }

    public Product getBestSellingProduct() {
        return bestSellingProduct;
    }

    public Product getHighestRevenueProduct() {
        return highestRevenueProduct;
    }

    public double getGrandTotalRevenue() {
        return grandTotalRevenue;
    }
}
