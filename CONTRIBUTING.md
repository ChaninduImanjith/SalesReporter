# Contributing to SalesReporter

## Commit Guidelines

This project follows the "commit little and often" principle. Each commit should represent one logical unit of work:

- **One class per commit** when adding new functionality
- **One fix per commit** when addressing bugs
- **Clear messages** that explain what changed and why

### Good commit messages
```
Add SalesCalculator revenue logic
Add unit tests for best-seller detection
Fix CSV parsing for rows with trailing commas
Update README with error handling examples
```

### Bad commit messages
```
update
fix stuff
wip
final version
```

## Branch Strategy

- `main` — stable, always buildable
- `feature/*` — one branch per team member, merged via Pull Request
- Never commit directly to `main`

## Pull Request Process

1. Push your feature branch
2. Open a PR from your branch into `main`
3. Request a review from a teammate
4. Merge after approval

## Code Standards

- Java 21+ features allowed
- Javadoc on all public classes and methods
- Follow SOLID principles
- No raw stack traces to users — catch and report gracefully
