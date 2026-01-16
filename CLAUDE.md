# CLAUDE.md

This file provides guidance for Claude Code when working with this repository.

## Project Overview

**ms-base-lib** is a Java library providing foundational base types and
standardized components for microservices applications. 

- **Language**: Java 25 (Amazon Corretto)
- **Build System**: Gradle 9.1.0+ with Kotlin DSL
- **Base Package**: `com.rubensgomes.msbaselib`

## Build Commands

```bash
# Build the project
./gradlew --info clean build

# Run tests
./gradlew --info clean test

# Apply code formatting (Spotless with Google Java Format)
./gradlew :lib:spotlessApply

# Create JAR
./gradlew --info jar

# Check Java toolchains
./gradlew -q javaToolchains
```

## Project Structure

```
lib/src/main/java/com/rubensgomes/msbaselib/
├── Status.java                      # Core status enumeration for request/response lifecycle
├── package-info.java
└── error/                           # Error handling framework
    ├── ApplicationError.java        # Error interface
    ├── ApplicationErrorCode.java    # Error code interface
    └── impl/
        ├── ApplicationErrorImpl.java      # Error implementation (uses Lombok @Data)
        └── ApplicationErrorCodeImpl.java  # Error code enumeration (24 predefined codes)
```

## Code Conventions

- **Formatting**: Google Java Format enforced via Spotless (run
  `./gradlew :lib:spotlessApply` before committing)
- **Validation**: Use Jakarta Bean Validation annotations (`@NotBlank`,
  `@NotNull`, `@Nullable`)
- **Logging**: SLF4J with Lombok `@Slf4j` annotation
- **Boilerplate**: Lombok annotations (`@Data`, `@Slf4j`) for reducing
  boilerplate
- **Documentation**: Javadoc on all public classes/methods with
  package-info.java files
- **Error Codes**: Follow format `[CATEGORY][TYPE][NUMBER]` (e.g., `BUSGN001`,
  `SECGN003`)
    - Categories: BUS (Business), PAY (Payment), RES (Resource), SEC (Security),
      SYS (System), VAL (Validation)
    - Types: GN (Generic), MS (MicroService-specific)
    - Number: 3-digit sequential (001-999)

## Testing

- **Framework**: JUnit 5 (Jupiter) with parameterized tests
- **Patterns**: Nested test classes, `@DisplayName` annotations, thread safety
  tests
- **Validation**: Jakarta Validator for constraint testing
- Tests are in `lib/src/test/java/` mirroring the main source structure

## Key Design Patterns

- **Immutable enums** for thread safety (Status, ApplicationErrorCodeImpl)
- **Interface-based contracts** for extensibility (ApplicationError,
  ApplicationErrorCode)
- **Hybrid immutability** in ApplicationErrorImpl: core fields are final,
  nativeErrorText is mutable
- **Technology-agnostic**: No Spring/Jakarta EE runtime dependencies required

## CI/CD Pipeline

GitHub Actions workflow (`.github/workflows/release.yml`) automates releases:

- **Trigger**: Pushes to `main` branch
- **Runner**: `ubuntu-latest`
- **Java Setup**: Amazon Corretto 25 via `actions/setup-java@v4`
- **Release Command**: `./gradlew release --info`

**Required Secrets**:
- `GITHUB_TOKEN` (automatic) - For checkout and Java setup
- `RUBENS_PAT_TOKEN` - Personal access token for release publishing

**Permissions**: The workflow requires `contents: write` and `packages: write`
permissions for creating releases and publishing packages.

## Publishing

Published to GitHub Packages. Requires `GITHUB_USER` and `GITHUB_TOKEN`
environment variables.
