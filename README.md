# Maven Enforcer Whitelist Report Tool

This application (and Maven plugin) will generate a report detailing all whitelisted dependencies.

It can be configured within a Maven project as such:

```xml
<plugin>
    <groupId>net.jonathangiles.tools</groupId>
    <artifactId>whitelistgenerator-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
        <baseDir>/directory/to/scan/in</baseDir>
        <reportFile>report.json</reportFile>
        <ignoredGA>
            <!-- Maven group ID and Artifact ID not to report on -->
            <param>org.slf4j:slf4j-api</param>
        </ignoredGA>
    </configuration>
</plugin>
```