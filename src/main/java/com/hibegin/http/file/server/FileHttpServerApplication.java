package com.hibegin.http.file.server;

import com.hibegin.common.util.LoggerUtil;
import com.hibegin.http.file.config.FileServerConfig;
import com.hibegin.http.server.WebServerBuilder;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHttpServerApplication {

    private static final Logger LOGGER = LoggerUtil.getLogger(FileHttpServerApplication.class);

    private static String version;
    private static final int DEFAULT_PORT = 7080;
    private static final Properties properties = new Properties();

    static {
        InputStream inputStream = FileHttpServerApplication.class.getResourceAsStream("/META-INF/maven/com.hibegin/simplewebserver-cli/pom.properties");
        if (inputStream != null) {
            try {
                properties.load(inputStream);
                version = properties.getProperty("version");
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "", e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "", e);
                }
            }
        } else {
            version = "0.1.x-dev";
        }
    }

    public static CommandLine cmd;

    public static void main(String[] args) {
        Options options = buildOptions();

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                formatter.printHelp("-help", options);
                System.exit(1);
                return;
            }
            if (cmd.hasOption("version")) {
                System.out.println(properties.getProperty("artifactId") + " version " + version);
                return;
            }
            if (cmd.hasOption("path")) {
                if (new File(cmd.getOptionValue("path")).isFile() || !new File(cmd.getOptionValue("path")).exists()) {
                    System.err.println("-path arg need is exists folder");
                    return;
                }
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("-help", options);

            System.exit(1);
            return;
        }

        Integer port = Integer.valueOf(cmd.getOptionValue("port", DEFAULT_PORT + ""));
        FileServerConfig fileServerConfig = new FileServerConfig(port);
        WebServerBuilder webServerBuilder = new WebServerBuilder.Builder().config(fileServerConfig).build();
        webServerBuilder.start();
    }

    private static Options buildOptions() {
        Options options = new Options();

        Option portOption = new Option("p", "port", true, "http file server listener port (default port " + DEFAULT_PORT + ")");
        portOption.setRequired(false);
        options.addOption(portOption);

        Option pathOption = new Option("f", "path", true, "base path (default current)");
        pathOption.setRequired(false);
        options.addOption(pathOption);

        Option helpOption = new Option("h", "help", false, "print help message");
        helpOption.setRequired(false);
        options.addOption(helpOption);

        Option versionOption = new Option("v", "version", false, "print " + properties.getProperty("artifactId") + " version");
        versionOption.setRequired(false);
        options.addOption(versionOption);

        Option autoIndexOption = new Option("i", "autoIndex", true, "auto index render file folder,\n0 is on, other value close");
        autoIndexOption.setRequired(false);
        options.addOption(autoIndexOption);
        return options;
    }
}
