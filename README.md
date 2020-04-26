# Berries-finder
### Build
You can build and run this project using the Gradle wrapper:
```bash
./gradlew clean build
```
This will run the tests and build the application.
### Run
To run the console application:
```bash
./gradlew bootRun
```

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

