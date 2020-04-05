package net.jonathangiles.tools.maven.whitelistgenerator.report;

import net.jonathangiles.tools.maven.whitelistgenerator.model.Result;

import java.nio.file.Path;

public class PlainTextReport implements Reporter {

    @Override
    public String getName() {
        return "plain-text";
    }

    @Override
    public void report(Result result, Path reportFile) {
        result.getParsedDocuments().forEach(parsedDocument -> {
            System.out.print(parsedDocument.getGroupId() + ":" + parsedDocument.getArtifactId() + ":" + parsedDocument.getVersion());
            System.out.println(" (located at " + parsedDocument.getPath() + ") has whitelisted the following dependencies:");
            parsedDocument.getWhitelistedDeps().stream().forEach(val -> System.out.println("  - " + val));
        });
    }
}
