package net.jonathangiles.tools.maven.whitelistgenerator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo(name = "report")
public class WhitelistMojo extends AbstractMojo {
    @Parameter
    private File baseDir;

    @Parameter
    private File reportFile;

    @Parameter
    private String[] ignoredGA;

    /** {@inheritDoc} */
    @Override
    public void execute() {
        if (baseDir == null) {
            getLog().error("baseDir is not specified - exiting");
            return;
        }
        if (reportFile == null) {
            getLog().error("reportFile is not specified - exiting");
            return;
        }

        Main main = new Main();
        main.setBaseDir(baseDir.toPath());
        main.setReportFile(reportFile.toPath());
        main.setLogCallback(msg -> getLog().info(msg));

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
