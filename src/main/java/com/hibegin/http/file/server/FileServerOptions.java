package com.hibegin.http.file.server;

import com.hibegin.common.util.LoggerUtil;
import com.hibegin.http.server.util.PathUtil;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileServerOptions {

    private static final Logger LOGGER = LoggerUtil.getLogger(FileServerOptions.class);

    public static final int DEFAULT_PORT = 7080;

    public static String artifactId = "simplewebserver-cli";

    private final Integer port;

    private final String rootPath;

    private final String location;

    private final Boolean autoIndex;

    public FileServerOptions(Integer port, String location, String rootPath, Boolean autoIndex) {
        this.port = port;
        this.location = location;
        this.rootPath = rootPath;
        this.autoIndex = autoIndex;
    }

    public Boolean getAutoIndex() {
        return autoIndex;
    }

    public String getRootPath() {
        return rootPath;
    }

    public Integer getPort() {
        return port;
    }

    public String getLocation() {
        return location;
    }

    private static Options buildOptions() {
        Options options = new Options();

        Option portOption = new Option("p", "port", true, "http file server listener port (default port " + DEFAULT_PORT + ")");
        portOption.setRequired(false);
        options.addOption(portOption);

        Option pathOption = new Option("f", "path", true, "base path (default current)");
        pathOption.setRequired(false);
        options.addOption(pathOption);

        Option locationOption = new Option("l", "location", true, "base uri (default /)");
        pathOption.setRequired(false);
        options.addOption(locationOption);

        Option helpOption = new Option("h", "help", false, "print help message");
        helpOption.setRequired(false);
        options.addOption(helpOption);

        Option versionOption = new Option("v", "version", false, "print " + artifactId + " version");
        versionOption.setRequired(false);
        options.addOption(versionOption);

        Option autoIndexOption = new Option("i", "autoIndex", true, "auto index render file folder,\n0 is disable, other value enable (default enable)");
        autoIndexOption.setRequired(false);
        options.addOption(autoIndexOption);
        return options;
    }

    public static String getVersion() {
        Properties properties = new Properties();
        InputStream inputStream = FileHttpServerApplication.class.getResourceAsStream("/META-INF/maven/com.hibegin/simplewebserver-cli/pom.properties");
        if (inputStream != null) {
            try {
                properties.load(inputStream);
                return properties.getProperty("version");
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "", e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "", e);
                }
            }
        }
        return "0.1.x-dev";
    }

    public static FileServerOptions parseToOptions(String[] args) {
        Options options = buildOptions();
        HelpFormatter formatter = new HelpFormatter();
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                formatter.printHelp("--help", options);
                return null;
            }
            if (cmd.hasOption("version")) {
                System.out.println(FileServerOptions.artifactId + " version " + getVersion());
                return null;
            }
            String path = PathUtil.getRootPath();
            if (cmd.hasOption("path")) {
                File file = new File(cmd.getOptionValue("path"));
                if (file.isFile() || !file.exists()) {
                    System.err.println("--path arg need is exists folder");
                    return null;
                } else {
                    path = file.toString();
                }
            }
            boolean enableIndex = true;
            if (cmd.hasOption("autoIndex")) {
                enableIndex = !Objects.equals(cmd.getOptionValue("autoIndex"), "0");
            }
            int port = FileServerOptions.DEFAULT_PORT;
            if (cmd.hasOption("port")) {
                port = Integer.parseInt(cmd.getOptionValue("port"));
            }
            String baseUri = "/";
            if (cmd.hasOption("location")) {
                baseUri = cmd.getOptionValue("location");
            }
            return new FileServerOptions(port, baseUri, path, enableIndex);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            formatter.printHelp("-help", options);
            return null;
        }
    }
}