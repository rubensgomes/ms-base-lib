# ms-base-lib

This is a single library module Gradle project. It implements a Java library containing base types to be shared among
microservices.

## Basic Commands

- Display Java Tools Installed

```shell
./gradlew -q javaToolchains
```

-- Update the gradlew wrapper version

```bash
./gradlew wrapper --gradle-version=9.1.0 --distribution-type=bin
```

- Clean, Build, Test, Assemble, Release, Publish

```bash
./gradlew --info clean
```

```bash
./gradlew :lib:spotlessApply
```

```bash
./gradlew --info clean build
```

```bash
# --info is required for Grdle to display logs from tests
./gradlew --info clean test
```

```bash
./gradlew --info jar
```

```bash
./gradlew --info assemble
```

```bash
git commit -m "fixes and refactorings" -a
git push
```

```bash
# only Rubens can release
./gradlew --info release
```

```bash
git checkout release
git pull
./gradlew --info publish
git checkout main
```

---
Author:  [Rubens Gomes](https://rubensgomes.com/)


