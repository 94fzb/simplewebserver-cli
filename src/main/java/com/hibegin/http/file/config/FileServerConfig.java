package com.hibegin.http.file.config;

import com.hibegin.common.util.LoggerUtil;
import com.hibegin.http.file.server.FileHttpServerApplication;
import com.hibegin.http.file.server.FileServerOptions;
import com.hibegin.http.server.config.AbstractServerConfig;
import com.hibegin.http.server.config.RequestConfig;
import com.hibegin.http.server.config.ResponseConfig;
import com.hibegin.http.server.config.ServerConfig;
import com.hibegin.http.server.util.PathUtil;

import java.util.Objects;
import java.util.logging.Logger;

public class FileServerConfig extends AbstractServerConfig {

    private static final Logger LOGGER = LoggerUtil.getLogger(FileServerConfig.class);
    public final FileServerOptions options;

    public FileServerConfig(FileServerOptions options) {
        this.options = options;
    }

    @Override
    public ServerConfig getServerConfig() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setNativeImageAgent(FileHttpServerApplication.NATIVE_IMAGE_AGENT);
        serverConfig.setApplicationName("simplewebserver-cli");
        serverConfig.setApplicationVersion(FileServerOptions.getVersion());
        serverConfig.setDisablePrintWebServerInfo(true);
        serverConfig.setDisableSavePidFile(true);
        serverConfig.addLocalFileStaticResourceMapper(options.getLocation(), Objects.requireNonNullElse(options.getRootPath(), PathUtil.getRootPath()), options.getAutoIndex());
        serverConfig.setPort(Objects.requireNonNullElse(options.getPort(), FileServerOptions.DEFAULT_PORT));
        if (Objects.equals(options.getAutoIndex(), true)) {
            serverConfig.setWelcomeFile(null);
        }
        LOGGER.info("Current router " + options.getLocation() + " -> " + options.getRootPath());
        return serverConfig;
    }

    @Override
    public RequestConfig getRequestConfig() {
        return new RequestConfig();
    }

    @Override
    public ResponseConfig getResponseConfig() {
        return new ResponseConfig();
    }
}
