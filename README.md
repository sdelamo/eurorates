**[Micronaut](https://micronaut.io) Java library to consume the [Euro foreign exchange reference rates](https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html)**

## Euro Foreign Exchange Reference Rates API Micronaut Java Library

- [Code Repository](https://github.com/sdelamo/eurorates)
- [Releases](https://github.com/sdelamo/eurorates/releases)
- [Java Docs](https://sdelamo.github.io/eurorates/javadoc/index.html)

This project is a Java libray to consume the [Euro foreign exchange reference rates](https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html) API. It is built with the [Micronaut](https://micronaut.io) Framework and you can use it in a Micronaut app or as a standalone library.

## Dependency snippet

To use it with https://gradle.org[Gradle]:

`implementation 'com.softamo:eurorates:XXXX'`

To use it with https://maven.apache.org[Maven]:

```xml
<dependency>
    <groupId>com.softamo</groupId>
    <artifactId>eurorates</artifactId>
    <version>xxx</version>
    <type>pom</type>
</dependency>
```

## Usage

If you want to use the library in Micronaut application, the library registers a bean of type `como.softamos.eurorates.EuroRatesApi` in the Micronaut's application context.

You can use the library without a Micronaut Application Context. In that case, to obtain a `EuroRatesApi` do:

```java
EuroRatesApi euroRatesApi = new ManualEuroRatesApi();
```

The api contains methods to obtain the current rates, historic rates and last 90 days rates.

## Build

This library uses https://gradle.org[Gradle].

It uses the plugins:

- [Gradle Build Scan Plugins](https://plugins.gradle.org/plugin/com.gradle.build-scan)
  
## Release instructions

### snapshot:

- Make sure version ends with `-SNAPSHOT`
- `./gradlew publish`

### release:

- bump up version
- Tag it. E.g. v1.0.0
- `./gradlew publishToSonatype closeSonatypeStagingRepository`

Go to `https://s01.oss.sonatype.org/#stagingRepositories` and release repository.
