package com.seng21222.salesreporter.output;

import com.seng21222.salesreporter.core.SalesSummary;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Saves the report to a text file at the given path.
 */
public class FileReportWriter implements ReportWriter {

    private final ReportFormatter formatter = new ReportFormatter();
    private final String filePath;

    public FileReportWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(SalesSummary summary) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(formatter.format(summary));
        }
    }
}
