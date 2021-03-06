package net.jonathangiles.tools.maven.whitelistgenerator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.w3c.dom.Document;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParsedDocument {
    private final Document document;

    @Expose
    private String groupId;

    @Expose
    private String artifactId;

    @Expose
    private String version;

    @Expose
    private final String path;

    @Expose
    @SerializedName("allowList")
    private List<String> allowListDeps;

    public ParsedDocument(final Document document, final Path path) {
        this.document = document;
        this.path = path.toString();
    }

    public void addWhiteListedDep(String value) {
        if (allowListDeps == null) {
            this.allowListDeps = new ArrayList<>();
        }
        allowListDeps.add(value);
    }

    public void setMavenGAV(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public Document getDocument() {
        return document;
    }

    public String getPath() {
        return path;
    }

    public List<String> getAllowListDeps() {
        return allowListDeps;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }
}