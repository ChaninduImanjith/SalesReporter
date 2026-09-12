package com.seng21222.salesreporter.output;

import com.seng21222.salesreporter.core.SalesSummary;
import com.seng21222.salesreporter.model.Product;

/**
 * Builds the report text shown to the user. Shared by every {@link ReportWriter}
 * so the formatting logic lives in exactly one place.
 */
public class ReportFormatter {

    public String format(SalesSummary summary) {
        StringBuilder sb = new StringBuilder();
        sb.append("============================================\n");
        sb.append(" PRODUCT SALES SUMMARY REPORT\n");
        sb.append("============================================\n\n");

        sb.append("--- Revenue Per Product ---\n");
        for (Product p : summary.getProducts()) {
            sb.append(String.format("%s %-15s %-12s $%6.2f%n",
                    p.productId(), p.productName(), p.category(), p.getRevenue()));
        }

        sb.append("\n--- Revenue Per Category ---\n");
        summary.getRevenuePerCategory().forEach((category, revenue) ->
                sb.append(String.format("%s : $%.2f%n", category, revenue)));

        sb.append("\n--- Highlights ---\n");
        sb.append(String.format("Best-Selling Product : %s (%d units)%n",
                summary.getBestSellingProduct().productName(),
                summary.getBestSellingProduct().quantitySold()));
        sb.append(String.format("Highest Revenue : %s ($%.2f)%n",
                summary.getHighestRevenueProduct().productName(),
                summary.getHighestRevenueProduct().getRevenue()));
        sb.append(String.format("Grand Total Revenue : $%.2f%n", summary.getGrandTotalRevenue()));
        sb.append("============================================\n");

        return sb.toString();
    }
}
