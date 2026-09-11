package com.seng21222.salesreporter.output;

import com.seng21222.salesreporter.core.SalesSummary;

/**
 * Strategy interface for delivering the finished report somewhere (console,
 * file, or - in the future - anything else) without the rest of the code
 * needing to know which one is in use.
 */
public interface ReportWriter {
    void write(SalesSummary summary) throws Exception;
}
