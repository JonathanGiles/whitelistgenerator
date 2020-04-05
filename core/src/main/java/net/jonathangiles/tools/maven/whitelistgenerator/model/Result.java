package net.jonathangiles.tools.maven.whitelistgenerator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Result {

    @Expose
    private final Set<String> fullWhitelist;

    @Expose
    @SerializedName("parsedDocuments")
    private final List<ParsedDocument> parsedDocuments;

    public Result(List<ParsedDocument> parsedDocuments) {
        this.parsedDocuments = new ArrayList<>();
        this.fullWhitelist = new TreeSet<>();

        for (ParsedDocument pd : parsedDocuments) {
            addParsedDocument(pd);
        }
    }

    public void addParsedDocument(ParsedDocument parsedDocument) {
        this.parsedDocuments.add(parsedDocument);
        this.fullWhitelist.addAll(parsedDocument.getWhitelistedDeps());
    }

    public List<ParsedDocument> getParsedDocuments() {
        return parsedDocuments;
    }
}
