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

### Intellij
This project was realised using IntelliJ IDEA 2020.1 (Community Edition). 
If you want to run this in Intellij, remember to enable the Annotation Processor under:
`Preferences | Build, Execution, Deployment | Compiler | Annotation Processors`

> Note 1: Product pages are fetched asynchronously. 
> This means that the order in which they are presented to you in the Json is not guaranteed
>
> Note 2: This project was realised with personal exercise in mind, 
> so at times it might seem like an over complicated implementation for the purpose it needs to serve. 
> The same is true for the functional style I've used in places. 
> I'm by no means an expert of that, so this projects served me as an exercise.
>


