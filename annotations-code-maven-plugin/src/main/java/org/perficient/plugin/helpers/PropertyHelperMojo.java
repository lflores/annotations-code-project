package org.perficient.plugin.helpers;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;

@Mojo(name = "property-helper", defaultPhase = LifecyclePhase.INITIALIZE)
public class PropertyHelperMojo extends AbstractMojo {

    static final String RED = "\033[0;31m";
    static final String GREEN = "\033[0;32m";
    static final String CYAN = "\033[0;36m";
    static final String LIGHT_RED = "\033[1;31m";
    static final String LIGHT_GREEN = "\033[1;32m";
    static final String YELLOW = "\033[1;33m";
    static final String LIGHT_BLUE = "\033[1;34m";
    static final String NC = "\033[0m"; // No Color

    static final String NEW_LINE = "\n";
    @Parameter(name = "project", readonly = true, defaultValue = "${project}")
    MavenProject project;

    @Parameter(name = "templatesFolderName", defaultValue = "templates")
    String templatesFolderName;

    @Parameter(name = "templateFileName", defaultValue = "application.properties")
    String templateFileName;
    @Parameter(name = "dataFolderName", defaultValue = "data")
    File dataFolderName;

    File resourcesFolder;
    File templatesFolder;
    File templateFile;
    File dataFolder;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (Objects.nonNull(project)) {
            project.getResources().forEach(resource -> {
                this.getLog().info("");
                this.getLog().info("--- " + GREEN + "Looking for resources in folder" + NC + " ---");
                this.getLog().info(resource.getDirectory());
                resourcesFolder = new File(resource.getDirectory());
                templatesFolder = new File(String.format("%s/%s", resource.getDirectory(), templatesFolderName));
                dataFolder = new File(String.format("%s/%s", resource.getDirectory(), "data"));
                templateFile = new File(String.format("%s/%s", templatesFolder.getPath(), templateFileName));

                if (!templatesFolder.isDirectory()
                        || !templateFile.isFile()
                        || !dataFolder.isDirectory()
                ) {
                    getLog().error(String.format("%s%s folder exist?:%s %b", YELLOW, templatesFolder.getAbsoluteFile(), NC, templatesFolder.isDirectory()));
                    getLog().error(String.format("%s%s folder exist?%s: %b", YELLOW, dataFolder.getAbsoluteFile(), NC, dataFolder.isDirectory()));
                    getLog().error(String.format("%s%s file? exist%s: %b", YELLOW, templateFile.getName(), NC, templateFile.isFile()));
                }
            });
            if (Objects.nonNull(templatesFolder)
                    && templatesFolder.isDirectory()
                    && Objects.nonNull(templateFile)
                    && templateFile.isFile()
                    && Objects.nonNull(dataFolder)
                    && dataFolder.isDirectory()
            ) {
                this.generateFiles();
            }
        }
    }

    private void generateFiles() throws MojoExecutionException {
        this.getLog().debug("Generating files from template");
        File[] files = dataFolder.listFiles((dir, name) -> name.endsWith(".properties"));
        assert files != null;
        for (File file : files) {
            this.processDataFile(file);
        }
    }

    private void processDataFile(File file) throws MojoExecutionException {
        getLog().debug("File: " + file.getName());
        Properties sourceProperties = this.loadProperties(file);
        File destinationFile = this.getDestinationFilename(file);
        getLog().debug(String.format("Loading %s file", file.getName()));
        getLog().debug(String.format("Destination file is %s", destinationFile.getPath()));
        this.replaceWildcards(sourceProperties, templateFile, destinationFile);
    }

    private File getDestinationFilename(File file) {
        String name = file.getName();
        return new File(String.format("%s/application-%s.properties", resourcesFolder.getPath(),
                name.substring(0, name.lastIndexOf("."))));
    }

    private Properties loadProperties(File propertiesFile) throws MojoExecutionException {
        final Properties properties = new Properties();
        try {
            final FileReader reader = new FileReader(propertiesFile);
            properties.load(reader);
        } catch (final IOException e) {
            throw new MojoExecutionException("Property file cannot be found.", e);
        }
        return properties;
    }

    private void replaceWildcards(final Properties p, final File origin, final File destination) throws MojoExecutionException {
        this.getLog().info("");
        this.getLog().info("--- " + GREEN + "Using template file" + NC + " ---");
        this.getLog().info(origin.getPath());
        this.getLog().info("");
        this.getLog().info("--- " + GREEN + "Putting result in" + NC + " ---");
        this.getLog().info(destination.getPath());
        String content;
        final List<String> localContent = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(origin);
            Scanner scanner = new Scanner(fis);
            StringBuilder contentBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                contentBuilder.append(scanner.nextLine());
                contentBuilder.append(System.lineSeparator()); // Agrega el separador de línea correspondiente
            }

            content = contentBuilder.toString();
            this.getLog().debug("File content: " + content);

            for (Map.Entry<Object, Object> next : p.entrySet()) {
                final String key = (String) next.getKey();
                final String value = (String) next.getValue();
                final String localContentPrefix = origin.getName();
                final boolean isOnlyLocalProperty = key.startsWith(localContentPrefix);
                if (isOnlyLocalProperty) {
                    this.getLog().debug("Adding local content to file: " + origin.getName() + " with: " + value);
                    localContent.add(value);
                } else {
                    this.getLog().debug("Replacing: " + key + " with: " + value);
                    content = content.replaceAll("\\#\\{" + key + "\\}", Matcher.quoteReplacement(value));
                }
            }
            final FileOutputStream output = new FileOutputStream(destination);
            Writer writer = new OutputStreamWriter(output);
            writer.write(content);
            writer.close();
        } catch (final IOException e) {
            throw new MojoExecutionException("Error while replacing wildcards", e);
        }
    }
}
