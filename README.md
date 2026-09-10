# SalesReporter

Command-line tool that reads daily product sales CSV data, computes a summary 
report, and outputs it to the console or a text file.

Built for SENG21222 - Software Construction, Assignment 1 (University of Kelaniya).

## Team Members
| Name | Role | Contribution |
|------|------|---------------|
| Kavindu | Member 1 | Core logic - computing the summary (revenue, best-seller, highest revenue) and writing the report |
| Chanindu | Member 2 | File I/O (CSV parsing), unit testing, SOLID principles |
| Minindu | Member 3 | Console interface (Main.java), exception handling, documentation |

## Package Structure
```
com.seng21222.salesreporter
│
├── model
│   └── Product.java                  # record: productId, productName, category, quantitySold, unitPrice
│
├── io
│   ├── ProductReader.java            # interface - readProducts(filePath)
│   └── CsvProductReader.java         # implementation - parses CSV, skips header/blank lines
│
├── core                              # (Member 1 - in progress)
│   ├── SalesSummary.java
│   └── SalesCalculator.java
│
├── output                            # (Member 1 - in progress)
│   ├── ReportWriter.java             # interface
│   ├── ReportFormatter.java
│   ├── ConsoleReportWriter.java
│   └── FileReportWriter.java
│
├── exception
│   ├── InvalidDataException.java     # thrown on malformed CSV rows / bad numeric data
│   └── InvalidOutputMethodException.java   # (Member 3 - in progress)
│
└── Main.java                         # (Member 3 - in progress) - entry point, CLI args, error handling

test/java/com.seng21222.salesreporter
├── io
│   └── CsvProductReaderTest.java     # 5 tests - valid parse, missing file, malformed row, bad number, blank lines
└── core
    └── SalesCalculatorTest.java      # (pending - after core is merged)
```

## How to Build
```bash
mvn clean package
```

## How to Run
```bash
java -jar target/SalesReporter.jar <csv-file-path> <output-method> [output-file-path]
```

Example:
```bash
java -jar target/SalesReporter.jar sales.csv console
java -jar target/SalesReporter.jar sales.csv file report.txt
```

## Running Tests
```bash
mvn test
```

## Status
- [x] Project setup (Maven, package structure)
- [x] Product model
- [x] File I/O - CSV parsing with unit tests (Member 2)
- [ ] Core logic - revenue/summary calculation (Member 1)
- [ ] Console interface & exception handling (Member 3)