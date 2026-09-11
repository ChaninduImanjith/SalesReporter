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
├── exception                         # (Member 3)
│   ├── InvalidDataException.java     # thrown on malformed CSV rows / bad numeric data
│   └── InvalidOutputMethodException.java   # thrown on an unknown output method / missing output path
│
└── Main.java                         # (Member 3) - entry point, CLI args, error handling

test/java/com.seng21222.salesreporter
├── io
│   └── CsvProductReaderTest.java     # 5 tests - valid parse, missing file, malformed row, bad number, blank lines
└── core
    └── SalesCalculatorTest.java      # (pending - after core is merged)
```

## Prerequisites
- JDK 21 (`java -version`) - the pom targets Java 21, so any newer JDK also builds it
- Maven 3.9+ (IntelliJ IDEA ships one, so an IDE install alone is enough)
- Git

## How to Build
```bash
mvn clean package
```

> `Main.java` calls into `core` and `output`, so the build only goes green once
> Member 1's `feature/core-logic` branch is merged. Until then those two packages
> are empty and compilation stops there.

## How to Run
```bash
java -jar target/SalesReporter.jar <csv-file-path> <output-method> [output-file-path]
```

Example, using the sample data committed at the repo root:
```bash
java -jar target/SalesReporter.jar sales.csv console
java -jar target/SalesReporter.jar sales.csv file report.txt
```

## Error Handling
Every failure is reported as a single `Error: ...` line on standard error and the
process exits with status `1`; a stack trace is never shown. A successful run
exits with status `0`.

| Situation | Message the user sees |
|-----------|-----------------------|
| Fewer than 2 arguments | the usage block, then `Error: Expected at least 2 arguments but received N ...` |
| Output method is neither `console` nor `file` | `Error: Invalid output method 'x'. Use 'console' or 'file'.` |
| `file` chosen with no output path | `Error: An output-file-path is required when the output method is 'file'.` |
| CSV file does not exist | `Error: CSV file not found: <path>` |
| CSV row malformed / not a number / file empty | `Error: The CSV data is invalid - <row detail>` |
| Output file cannot be written | `Error: A file could not be read or written - <detail>` |
| Anything unforeseen | `Error: Unexpected error (<class>) - <detail>` |

## Running Tests
```bash
mvn test
```

## Status
- [x] Project setup (Maven, package structure)
- [x] Product model
- [x] File I/O - CSV parsing with unit tests (Member 2)
- [ ] Core logic - revenue/summary calculation (Member 1)
- [x] Console interface & exception handling (Member 3) - `Main.java`, `InvalidOutputMethodException`, `InvalidDataException`
- [x] Documentation - README build/run/error reference, Javadoc on the console and exception layers (Member 3)