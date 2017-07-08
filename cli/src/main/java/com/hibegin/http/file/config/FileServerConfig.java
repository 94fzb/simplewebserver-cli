package com.hibegin.http.file.config;

import com.hibegin.http.file.intercepter.FileInterceptor;
import com.hibegin.http.server.config.AbstractServerConfig;
import com.hibegin.http.server.config.RequestConfig;
import com.hibegin.http.server.config.ResponseConfig;
import com.hibegin.http.server.config.ServerConfig;

public class FileServerConfig extends AbstractServerConfig {
    private Integer port;

    public FileServerConfig(Integer port) {
        this.port = port;
    }

    @Override
    public ServerConfig getServerConfig() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.addInterceptor(FileInterceptor.class);
        serverConfig.setPort(port);
        serverConfig.setWelcomeFile(null);
        return serverConfig;
    }

    @Override
    public RequestConfig getRequestConfig() {
        return null;
    }

    @Override
    public ResponseConfig getResponseConfig() {
        return null;
    }
}
