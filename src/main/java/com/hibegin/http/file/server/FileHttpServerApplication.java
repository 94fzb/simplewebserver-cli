package com.hibegin.http.file.server;

import com.hibegin.http.server.WebServerBuilder;
import com.hibegin.http.server.util.PathUtil;

import java.util.Objects;

public class FileHttpServerApplication {

    public static boolean NATIVE_IMAGE_AGENT = false;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %5$s%6$s%n");
    }

    public static WebServerBuilder webServerBuilder(String[] args) {
        if (args == null || args.length == 0) {
            return new WebServerBuilder.Builder().config(new com.hibegin.http.file.config.FileServerConfig(new FileServerOptions(FileServerOptions.DEFAULT_PORT, "/", PathUtil.getRootPath(), true))).build();
        }
        FileServerOptions options = FileServerOptions.parseToOptions(args);
        if (Objects.isNull(options)) {
            return null;
        }
        return new WebServerBuilder.Builder().config(new com.hibegin.http.file.config.FileServerConfig(options)).build();
    }

    public static void main(String[] args) {
        WebServerBuilder webServerBuilder = webServerBuilder(args);
        if (Objects.isNull(webServerBuilder)) {
            return;
        }
        webServerBuilder.start();
    }
}
