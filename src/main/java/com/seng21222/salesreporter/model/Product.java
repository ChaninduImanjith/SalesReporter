package com.seng21222.salesreporter.model;

public record Product(
        String productId,
        String productName,
        String category,
        int quantitySold,
        double unitPrice
) {
    public double getRevenue() {
        return quantitySold * unitPrice;
    }
}