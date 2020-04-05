package net.jonathangiles.tools.maven.whitelistgenerator.report;

import com.google.gson.GsonBuilder;
import net.jonathangiles.tools.maven.whitelistgenerator.model.Result;

import java.io.FileWriter;
import java.nio.file.Path;

public class JSONReport implements Reporter {

    @Override
    public String getName() {
        return "json";
    }

    @Override
    public void report(Result result, Path reportFile) {
        try (FileWriter writer = new FileWriter(reportFile.toFile()))  {
            new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                    .toJson(result, writer);

            System.out.println("JSON report written to " + reportFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
