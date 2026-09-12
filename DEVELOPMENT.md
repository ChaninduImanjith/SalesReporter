# Development Guide

## Prerequisites

- **JDK 21+** — check with `java -version`
- **Maven 3.9+** — check with `mvn -version` (IntelliJ bundles it)
- **Git** — check with `git --version`

## Build

```bash
mvn clean package
```

Output: `target/SalesReporter.jar`

## Run

```bash
java -jar target/SalesReporter.jar <csv-file-path> <output-method> [output-file-path]
```

### Examples

```bash
# Print report to console
java -jar target/SalesReporter.jar sales.csv console

# Save report to file
java -jar target/SalesReporter.jar sales.csv file report.txt

# Using the quickstart script (Windows)
.\quickstart.ps1 -CsvFile sales.csv -OutputMethod console
```

## Test

```bash
mvn test
```

## Project Structure

```
src/main/java/com/seng21222/salesreporter/
├── model/          # Product record (Member 2)
├── io/             # CSV parsing (Member 2)
├── core/           # Calculation logic (Member 1)
├── output/         # Report writers (Member 1)
├── exception/      # Custom exceptions (Member 3)
└── Main.java       # CLI entry point (Member 3)
```

## Troubleshooting

### Build fails with "package does not exist"

The `core` and `output` packages are empty until Member 1 merges their branch. Pull `main` after they merge:

```bash
git checkout main
git pull origin main
git checkout feature/console-exceptions
git merge main
```

### Maven not found

If `mvn` is not on your PATH, use IntelliJ's bundled version:

```bash
"C:\Program Files\JetBrains\IntelliJ IDEA 2025.2.1\plugins\maven\lib\maven3\bin\mvn.cmd" clean package
```

### Exit code 1 with "Error: ..."

The tool caught an expected failure (missing file, bad CSV, wrong arguments). Check the message and retry.

## Branch Workflow

1. Work on your `feature/*` branch
2. Commit often with clear messages
3. Push and open a PR into `main`
4. Get a teammate to review
5. Merge and pull `main` locally
