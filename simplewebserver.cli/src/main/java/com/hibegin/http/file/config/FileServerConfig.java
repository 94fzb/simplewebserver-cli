package com.hibegin.http.file.config;

import com.hibegin.common.util.LoggerUtil;
import com.hibegin.http.file.intercepter.FileInterceptor;
import com.hibegin.http.file.server.SimpleWebServerCliOptions;
import com.hibegin.http.server.config.AbstractServerConfig;
import com.hibegin.http.server.config.RequestConfig;
import com.hibegin.http.server.config.ResponseConfig;
import com.hibegin.http.server.config.ServerConfig;

import java.util.Objects;
import java.util.logging.Logger;

public class FileServerConfig extends AbstractServerConfig {

    private static final Logger LOGGER = LoggerUtil.getLogger(FileServerConfig.class);
    public final SimpleWebServerCliOptions options;

    public FileServerConfig(SimpleWebServerCliOptions options) {
        this.options = options;
    }

    public SimpleWebServerCliOptions getOptions() {
        return options;
    }

    @Override
    public ServerConfig getServerConfig() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.addInterceptor(FileInterceptor.class);
        serverConfig.setPort(Objects.requireNonNullElse(options.getPort(), SimpleWebServerCliOptions.DEFAULT_PORT));
        serverConfig.setWelcomeFile(null);
        if (options.getPath() != null) {
            LOGGER.info("Current workPath " + options.getPath());
        }
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
