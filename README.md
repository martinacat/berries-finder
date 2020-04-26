# Berries-finder
### Requirements
This project was realised using 
* java version "11.0.5" 2019-10-15 LTS
* Gradle wrapper - no need for you to have Gradle installed

### Build
You can build this project using the Gradle wrapper:
```bash
./gradlew clean build
```
This will run the tests and build the application.
### Run
To run the console application:
```bash
./gradlew bootRun
```

### Test coverage
To generate test coverage reports run:
```bash
./gradlew jacocoTR
```
after having built the project.
This will generate test reports under `build/reports/jacoco`.

### Enable Gradle Logging
Gradle logging has been set to `plain` to provide a less cluttered user experience.
If you want to use the default Gradle logging you can go to `gradle.properties` and get rid of:
```yaml
org.gradle.console=plain
```

> Note 1: Product pages are fetched asynchronously. 
> This means that the order in which they are presented to you in the Json is not guaranteed
>
> Note 2: I understand this is an overkill implementation for the purpose it needs to serve, 
> however this was done as a personal exercise more than anything else 

