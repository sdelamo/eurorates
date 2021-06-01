**[Micronaut](https://micronaut.io) Java library to consume the [Euro foreign exchange reference rates](https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html)**

[Docs](https://sdelamo.github.io/eurorates/index.html)

## Release instructions

snapshot:

- Make sure version ends with `-SNAPSHOT`
- `./gradlew publish`

release: 

- bump up version 
- Tag it. E.g. v1.0.0 
- `./gradlew publishToSonatype closeSonatypeStagingRepository`

