# Maven Enforcer Whitelist Report Tool

This application (and Maven plugin) will generate a report detailing all whitelisted dependencies.

It can be configured within a Maven project as such:

```xml
<plugin>
    <groupId>net.jonathangiles.tools</groupId>
    <artifactId>whitelistgenerator-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
        <!-- baseDir can be absolute, or relative to the pom file (if there is no leading '/' or drive letter) -->
        <baseDir>/directory/to/scan/in</baseDir>

        <!-- The location to write the report. Can be absolute or relative to pom file -->.
        <reportFile>report.json</reportFile>
        <ignoredGA>
            <!-- Maven group ID and Artifact ID not to report on, e.g. if some modules have whitelists we do not want to publish -->
            <param>org.slf4j:slf4j-api</param>
        </ignoredGA>
    </configuration>
</plugin>
```

To use, run with the maven goal of `whitelistgenerator:report`.

## Building and Releasing

* To build: `mvn clean install`
* To release: `mvn clean deploy -Prelease`
* To upgrade versions: `mvn versions:set -DnewVersion=1.0.1`