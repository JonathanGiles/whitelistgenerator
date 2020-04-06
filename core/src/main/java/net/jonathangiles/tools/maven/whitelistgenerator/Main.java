package net.jonathangiles.tools.maven.whitelistgenerator;

import net.jonathangiles.tools.maven.whitelistgenerator.model.Result;
import net.jonathangiles.tools.maven.whitelistgenerator.report.JSONReport;
import net.jonathangiles.tools.maven.whitelistgenerator.model.ParsedDocument;
import net.jonathangiles.tools.maven.whitelistgenerator.report.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private Path baseDir;
    private Path reportFile;

    private DocumentBuilder builder;
    private XPath xpath;
    private XPathExpression isMavenProjectExpression;
    private XPathExpression bannedDepsWhiteListExpression;

    private List<String> ignoredGA;

    private Reporter reporter;

    public void setBaseDir(Path baseDir) {
        this.baseDir = baseDir;
    }

    public void setReportFile(Path reportFile) {
        this.reportFile = reportFile;
    }

    public void addIgnoredMavenGA(String ga) {
        if (ignoredGA == null) {
            ignoredGA = new ArrayList<>();
        }
        ignoredGA.add(ga);
    }

    public void run() {
        // setup XML parser
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);
        try {
            builderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            builder = builderFactory.newDocumentBuilder();

            xpath = XPathFactory.newInstance().newXPath();
            isMavenProjectExpression = xpath.compile("/project");
            bannedDepsWhiteListExpression = xpath.compile("/project/build/plugins/plugin/executions/execution/configuration/rules/bannedDependencies/includes/include");
        } catch (ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }

        // setup reporter
        reporter = new JSONReport();

        try (Stream<Path> paths = Files.walk(baseDir)) {
            Result result = new Result(paths
                .filter(p -> p.toString().endsWith(".xml"))
                .map(this::checkIfMavenPom)
                .flatMap(this::flatMapOptional)
                .map(this::checkForWhiteList)
                .flatMap(this::flatMapOptional)
                .collect(Collectors.toList()));

            reporter.report(result, reportFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<ParsedDocument> checkIfMavenPom(Path p) {
        try (FileInputStream fileIS = new FileInputStream(p.toFile())) {
            Document xmlDocument = builder.parse(fileIS);

            Element node = (Element) isMavenProjectExpression.evaluate(xmlDocument, XPathConstants.NODE);
            if (node == null) {
                return Optional.empty();
            }

            final String groupId = getElementTextContent("groupId", node);
            final String artifactId = getElementTextContent("artifactId", node);

            if (ignoredGA != null && ignoredGA.contains(groupId + ":" + artifactId)) {
                return Optional.empty();
            }

            final ParsedDocument parsedDocument = new ParsedDocument(xmlDocument, baseDir.relativize(p));

            final String version = getElementTextContent("version", node);
            parsedDocument.setMavenGAV(groupId, artifactId, version);

            return Optional.of(parsedDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private String getElementTextContent(String elementName, Element parent) {
        if (parent == null) return "";
        NodeList nodeList = parent.getElementsByTagName(elementName);
        if (nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node n = nodeList.item(i);
                if (n.getParentNode().equals(parent)) {
                    return n.getTextContent();
                }
            }
        }
        return "";
    }

    private Optional<ParsedDocument> checkForWhiteList(ParsedDocument parsedDocument) {
        try {
            NodeList nodeList = (NodeList) bannedDepsWhiteListExpression.evaluate(parsedDocument.getDocument(), XPathConstants.NODESET);
            if (nodeList.getLength() > 0) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    parsedDocument.addWhiteListedDep(nodeList.item(i).getTextContent());
                }
                return Optional.of(parsedDocument);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private <T> Stream<T> flatMapOptional(Optional<T> optional) {
        return optional.isPresent() ? Stream.of(optional.get()) : Stream.empty();
    }
}
