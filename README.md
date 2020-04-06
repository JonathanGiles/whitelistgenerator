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

## What does a report look like?

The default report output is in JSON, and it takes the following form:

```json
{
  "fullWhitelist": [
    "com.azure:*",
    "com.fasterxml.jackson.core:jackson-annotations",
    "com.fasterxml.jackson.core:jackson-core",
    "com.fasterxml.jackson.core:jackson-databind",
    "com.fasterxml.jackson.dataformat:jackson-dataformat-xml",
    "com.fasterxml.jackson.datatype:jackson-datatype-jsr310",
    "com.fasterxml.jackson.module:jackson-module-afterburner",
    "com.google.code.findbugs:jsr305",
    "com.microsoft.azure:msal4j",
    "com.microsoft.azure:qpid-proton-j-extensions",
    "com.nimbusds:oauth2-oidc-sdk",
    "com.squareup.okhttp3:okhttp",
    "io.dropwizard.metrics:metrics-core",
    "io.micrometer:micrometer-core",
    "io.netty:netty-buffer",
    "io.netty:netty-codec-http",
    "io.netty:netty-codec-http2",
    "io.netty:netty-handler",
    "io.netty:netty-handler-proxy",
    "io.netty:netty-tcnative-boringssl-static",
    "io.netty:netty-transport-native-epoll",
    "io.netty:netty-transport-native-unix-common",
    "io.opentelemetry",
    "io.projectreactor.netty",
    "io.projectreactor:reactor-core",
    "net.java.dev.jna",
    "org.apache.qpid:proton-j",
    "org.nanohttpd:nanohttpd",
    "org.slf4j:slf4j-api"
  ],
  "parsedDocuments": [
    {
      "groupId": "com.azure",
      "artifactId": "azure-core-http-netty",
      "version": "1.5.0-beta.1",
      "path": "core/azure-core-http-netty/pom.xml",
      "whitelist": [
        "com.azure:*",
        "io.projectreactor.netty",
        "io.netty:netty-buffer",
        "io.netty:netty-codec-http",
        "io.netty:netty-codec-http2",
        "io.netty:netty-handler",
        "io.netty:netty-handler-proxy",
        "io.netty:netty-transport-native-unix-common",
        "io.netty:netty-transport-native-epoll"
      ]
    },
    {
      "groupId": "com.azure",
      "artifactId": "azure-core-amqp",
      "version": "1.1.0-beta.1",
      "path": "core/azure-core-amqp/pom.xml",
      "whitelist": [
        "com.azure:*",
        "org.apache.qpid:proton-j",
        "com.microsoft.azure:qpid-proton-j-extensions"
      ]
    },
    {
      "groupId": "com.azure",
      "artifactId": "azure-core",
      "version": "1.4.0-beta.1",
      "path": "core/azure-core/pom.xml",
      "whitelist": [
        "org.slf4j:slf4j-api",
        "io.projectreactor:reactor-core",
        "io.netty:netty-tcnative-boringssl-static",
        "com.fasterxml.jackson.dataformat:jackson-dataformat-xml",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310",
        "com.google.code.findbugs:jsr305"
      ]
    },
    {
      "groupId": "com.azure",
      "artifactId": "azure-core-tracing-opentelemetry",
      "version": "1.0.0-beta.4",
      "path": "core/azure-core-tracing-opentelemetry/pom.xml",
      "whitelist": [
        "com.azure:*",
        "io.opentelemetry"
      ]
    },
    {
      "groupId": "com.azure",
      "artifactId": "azure-core-http-okhttp",
      "version": "1.3.0-beta.1",
      "path": "core/azure-core-http-okhttp/pom.xml",
      "whitelist": [
        "com.azure:*",
        "com.squareup.okhttp3:okhttp"
      ]
    },
    {
      "groupId": "com.azure",
      "artifactId": "azure-identity",
      "version": "1.1.0-beta.3",
      "path": "identity/azure-identity/pom.xml",
      "whitelist": [
        "com.azure:*",
        "com.nimbusds:oauth2-oidc-sdk",
        "com.microsoft.azure:msal4j",
        "org.nanohttpd:nanohttpd",
        "net.java.dev.jna"
      ]
    },
    {
      "groupId": "com.azure",
      "artifactId": "azure-cosmos",
      "version": "4.0.1-beta.2",
      "path": "cosmos/azure-cosmos/pom.xml",
      "whitelist": [
        "com.azure:*",
        "org.slf4j:slf4j-api",
        "io.projectreactor:reactor-core",
        "com.fasterxml.jackson.core:jackson-core",
        "com.fasterxml.jackson.core:jackson-annotations",
        "com.fasterxml.jackson.core:jackson-databind",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310",
        "com.fasterxml.jackson.module:jackson-module-afterburner",
        "io.micrometer:micrometer-core",
        "io.dropwizard.metrics:metrics-core",
        "io.projectreactor.netty",
        "io.netty:netty-codec-http",
        "io.netty:netty-codec-http2",
        "io.netty:netty-handler",
        "io.netty:netty-handler-proxy",
        "io.netty:netty-transport-native-epoll",
        "com.google.code.findbugs:jsr305"
      ]
    }
  ]
}
```

## Building and Releasing

* To build: `mvn clean install`
* To release: `mvn clean deploy -Prelease`
* To upgrade versions: `mvn versions:set -DnewVersion=1.0.1`