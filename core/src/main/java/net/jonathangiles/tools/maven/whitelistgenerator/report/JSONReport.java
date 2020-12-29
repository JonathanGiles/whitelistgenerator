package net.jonathangiles.tools.maven.whitelistgenerator.report;

import com.google.gson.GsonBuilder;
import net.jonathangiles.tools.maven.whitelistgenerator.model.Result;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class JSONReport implements Reporter {

    @Override
    public String getName() {
        return "json";
    }

    @Override
    public void report(Result result, Path reportFilePath) {
        FileWriter writer = null;
        try {
            File reportFile = reportFilePath.toFile();
            if (!reportFile.exists()) {
                System.out.println("Creating file " + reportFile.getAbsolutePath());
                reportFile.getParentFile().mkdirs();
                reportFile.createNewFile();
            }
            writer = new FileWriter(reportFile);
            new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                    .toJson(result, writer);

            System.out.println("JSON report written to " + reportFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
