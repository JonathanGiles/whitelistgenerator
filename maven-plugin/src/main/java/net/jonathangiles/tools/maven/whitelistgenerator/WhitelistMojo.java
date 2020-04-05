package net.jonathangiles.tools.maven.whitelistgenerator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "report")
public class WhitelistMojo extends AbstractMojo {
    @Parameter
    private String baseDir;

    @Parameter
    private String reportFile;

    @Parameter
    private String[] ignoredGA;

    /** {@inheritDoc} */
    @Override
    public void execute() {
        if (baseDir == null || baseDir.isEmpty()) {
            getLog().error("baseDir is not specified - exiting");
            return;
        }
        if (reportFile == null || reportFile.isEmpty()) {
            getLog().error("reportFile is not specified - exiting");
            return;
        }

        Main main = new Main();
        main.setBaseDir(baseDir);
        main.setReportFile(reportFile);

        if (ignoredGA != null) {
            for (String ga : ignoredGA) {
                main.addIgnoredMavenGA(ga);
            }
        }

        getLog().info("Running with configuration: [ " +
                              "baseDir='" + baseDir + "', " +
                              "reportFile='" + reportFile + "'" +
                              " ]");
        main.run();
    }
}
