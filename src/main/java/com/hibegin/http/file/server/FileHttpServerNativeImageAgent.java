package com.hibegin.http.file.server;

import com.hibegin.http.server.WebServerBuilder;

import java.util.Objects;

public class FileHttpServerNativeImageAgent {

    public static void main(String[] args) throws InterruptedException {
        FileHttpServerApplication.NATIVE_IMAGE_AGENT = true;
        WebServerBuilder webServerBuilder = FileHttpServerApplication.webServerBuilder(new String[]{"-p", "0", "-f", "/"});
        if (Objects.nonNull(webServerBuilder)) {
            webServerBuilder.startWithThread();
        }
        Thread.sleep(2000);
        System.exit(0);
    }
}
