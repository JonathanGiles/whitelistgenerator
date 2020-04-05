package net.jonathangiles.tools.maven.whitelistgenerator.report;

import net.jonathangiles.tools.maven.whitelistgenerator.model.Result;

import java.nio.file.Path;

public interface Reporter {

    /**
     * The name of the reporter, for use by the user of the app to request that this reporter be run.
     *
     * @return The name of the reporter.
     */
    String getName();

    void report(Result result, Path outputFile);
}
