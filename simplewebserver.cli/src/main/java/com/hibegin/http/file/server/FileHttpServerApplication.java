package com.hibegin.http.file.server;

import com.hibegin.common.util.LoggerUtil;
import com.hibegin.http.file.config.FileServerConfig;
import com.hibegin.http.server.WebServerBuilder;
import fr.cril.cli.ClassParser;
import fr.cril.cli.CliArgsParser;
import fr.cril.cli.CliOptionDefinitionException;
import fr.cril.cli.CliUsageException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHttpServerApplication {

    private static final Logger LOGGER = LoggerUtil.getLogger(FileHttpServerApplication.class);
    private static final SimpleWebServerCliOptions simpleWebServerCliOptions = new SimpleWebServerCliOptions();
    public static FileServerConfig fileServerConfig = new FileServerConfig(simpleWebServerCliOptions);

    private static String version;
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

    private static void launch() {
        WebServerBuilder webServerBuilder = new WebServerBuilder.Builder().config(fileServerConfig).build();
        webServerBuilder.start();
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            launch();
            return;
        }
        final ClassParser<SimpleWebServerCliOptions> classParser = new ClassParser<>(SimpleWebServerCliOptions.class); // the annotation parser
        final CliArgsParser<SimpleWebServerCliOptions> argsParser = new CliArgsParser<>(classParser);
        try {
            argsParser.parse(simpleWebServerCliOptions, args);
            List<String> parameters = argsParser.getParameters();
            if (parameters.contains("help")) {
                classParser.printOptionUsage(new PrintWriter(System.out));
                System.exit(1);
                return;
            }
            if (parameters.contains("version")) {
                System.out.println(properties.getProperty("artifactId") + " version " + version);
                return;
            }
            if (parameters.contains("path")) {
                String path = simpleWebServerCliOptions.getPath();
                if (new File(path).isFile() || !new File(path).exists()) {
                    System.err.println("-path arg need is exists folder");
                    return;
                }
            }
        } catch (CliUsageException | CliOptionDefinitionException e) {
            System.out.println("error:" + e.getMessage() + "\n");
            classParser.printOptionUsage(new PrintWriter(System.out));
            return;
        }
        launch();
    }
}
