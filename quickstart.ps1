# SalesReporter Quick Start Script
# Run this from the repo root after building with Maven

param(
    [string]$CsvFile = "sales.csv",
    [string]$OutputMethod = "console",
    [string]$OutputFile = "report.txt"
)

$jar = "target/SalesReporter.jar"

if (-not (Test-Path $jar)) {
    Write-Host "Building SalesReporter..." -ForegroundColor Yellow
    mvn clean package -q
}

Write-Host "Running: java -jar $jar $CsvFile $OutputMethod $OutputFile" -ForegroundColor Cyan
java -jar $jar $CsvFile $OutputMethod $OutputFile

if ($LASTEXITCODE -eq 0) {
    Write-Host "Success!" -ForegroundColor Green
} else {
    Write-Host "Failed with exit code $LASTEXITCODE" -ForegroundColor Red
}
