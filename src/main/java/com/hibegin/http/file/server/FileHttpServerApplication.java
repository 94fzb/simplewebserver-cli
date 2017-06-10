package com.hibegin.http.file.server;

import com.hibegin.http.file.config.FileServerConfig;
import com.hibegin.http.server.WebServerBuilder;
import org.apache.commons.cli.*;

import java.io.File;

public class FileHttpServerApplication {

    private static final String version = "0.0.1";
    private static final int DEFAULT_PORT = 7080;

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
                System.out.println("simplewebserver-cli version " + version);
                return;
            }
            if (cmd.hasOption("path")) {
                if (new File(cmd.getOptionValue("path")).isFile() || !new File(cmd.getOptionValue("path")).exists()) {
                    System.err.println("-f arg need is exists folder");
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

        Option versionOption = new Option("v", "version", false, "print simplewebserver-cli version");
        versionOption.setRequired(false);
        options.addOption(versionOption);

        Option autoIndexOption = new Option("i", "autoIndex", true, "auto index render file folder,\n0 is on, other value close");
        autoIndexOption.setRequired(false);
        options.addOption(autoIndexOption);
        return options;
    }
}
