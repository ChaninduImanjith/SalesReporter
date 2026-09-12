# SalesReporter

SalesReporter is a command-line application that reads daily product sales data from a CSV file, computes a summary report, and outputs the report either to the console or to a text file.

Built for **SENG21222 - Software Construction, Assignment 1** at the **University of Kelaniya**.

## Team Members

| Name | Student ID | Role | Branch | Contribution |
|---|---|---|---|---|
| Kavindu Gimshan | SE/2023/001 | Member 1 | `feature/core-logic` | Core logic - computing the sales summary, including revenue per product/category, best-selling product, highest-revenue product, grand total, and formatting/writing the report to the console or a file |
| Chanindu Imanjith | SE/2023/022 | Member 2 | `feature/io-testing-solid` | File I/O - reading and parsing the CSV file into product data; unit testing across the project; SOLID design using interface-based reader and writer components |
| Minindu Rajapaksha | SE/2023/039 | Member 3 | `feature/console-exceptions` | Console interface - command-line argument parsing and orchestration in `Main.java`; exception handling for expected failure cases; project documentation |

## Package Structure

```text
com.seng21222.salesreporter
│
├── model
│   └── Product.java                        # record: productId, productName, category, quantitySold, unitPrice
│
├── io
│   ├── ProductReader.java                  # interface - readProducts(filePath)
│   └── CsvProductReader.java               # parses CSV, skips header/blank lines
│
├── core
│   ├── SalesSummary.java                   # holds computed results
│   └── SalesCalculator.java                # revenue, best-seller, highest-revenue, grand total
│
├── output
│   ├── ReportWriter.java                   # interface using the Strategy pattern
│   ├── ReportFormatter.java                # shared report text builder
│   ├── ConsoleReportWriter.java
│   └── FileReportWriter.java
│
├── exception
│   ├── InvalidDataException.java           # malformed CSV rows / bad numeric data
│   └── InvalidOutputMethodException.java   # unknown output method / missing output path
│
└── Main.java                               # entry point - CLI args, wiring, error handling

test/java/com.seng21222.salesreporter
│
├── io
│   └── CsvProductReaderTest.java           # 5 tests
│
└── core
    └── SalesCalculatorTest.java            # 5 tests
```

## Prerequisites

Before building and running the project, make sure the following are installed:

- **JDK 21**
  ```bash
  java -version
  ```

- **Maven 3.9+**
  - IntelliJ IDEA includes Maven, so installing IntelliJ IDEA is sufficient if you use its bundled Maven.

- **Git**

## How to Build

Run the following command from the project root directory:

```bash
mvn clean package
```

If the build is successful, Maven will generate the executable JAR file inside the `target` directory.

## How to Run

Use the following command:

```bash
java -jar target/SalesReporter.jar <csv-file-path> <output-method> [output-file-path]
```

### Console Output

```bash
java -jar target/SalesReporter.jar sales.csv console
```

### File Output

```bash
java -jar target/SalesReporter.jar sales.csv file report.txt
```

In the examples above, `sales.csv` is the sample CSV file located in the repository root.

## Error Handling

Every failure is reported as a single `Error: ...` message on standard error.

- A stack trace is never shown to the user.
- A successful run exits with status code `0`.
- A failed run exits with status code `1`.

| Situation | Message the user sees |
|---|---|
| Fewer than 2 arguments | Usage block, followed by `Error: Expected at least 2 arguments ...` |
| Output method is neither `console` nor `file` | `Error: Invalid output method 'x'. Use 'console' or 'file'.` |
| `file` selected without an output path | `Error: An output-file-path is required when the output method is 'file'.` |
| CSV file does not exist | `Error: CSV file not found: <path>` |
| CSV row is malformed, contains invalid numbers, or the file is empty | `Error: The CSV data is invalid - <row detail>` |
| Output file cannot be written | `Error: A file could not be read or written - <detail>` |

## Running Tests

Run the complete unit test suite with:

```bash
mvn test
```

The project contains **10 unit tests** in total.

### `CsvProductReaderTest` - 5 tests

Tests cover:

- Valid CSV parsing
- Missing input file
- Malformed CSV row
- Invalid numeric value
- Blank-line handling

### `SalesCalculatorTest` - 5 tests

Tests cover:

- Revenue per product
- Revenue per category
- Best-selling product
- Highest-revenue product
- Empty-list handling

## Project Status

- [x] Project setup - Maven and package structure
- [x] Product model
- [x] File I/O - CSV parsing with unit tests
- [x] Core logic - revenue and summary calculations with unit tests
- [x] Console interface and exception handling
- [x] Report output to console and file
- [x] Documentation

## Design Notes

The project follows SOLID principles by separating major responsibilities into independent components.

### Report Output Strategy

`ReportWriter` is an interface implemented by:

- `ConsoleReportWriter`
- `FileReportWriter`

This follows the **Strategy design pattern**.

A new output method, such as email output, can be added by creating another implementation of `ReportWriter` without modifying the existing report-writing logic.

### Product Input Abstraction

`ProductReader` is also defined as an interface.

`CsvProductReader` provides the current CSV implementation.

This allows support for additional input formats in the future by creating new `ProductReader` implementations without changing the rest of the application.

## Technologies Used

- Java 21
- Maven
- JUnit
- Git
- IntelliJ IDEA

## License

This project was developed for academic purposes as part of the **SENG21222 - Software Construction** module at the **University of Kelaniya**.
