package com.seng21222.salesreporter.output;

import com.seng21222.salesreporter.core.SalesSummary;

/**
 * Prints the report to standard output.
 */
public class ConsoleReportWriter implements ReportWriter {

    private final ReportFormatter formatter = new ReportFormatter();

    @Override
    public void write(SalesSummary summary) {
        System.out.println(formatter.format(summary));
    }
}
