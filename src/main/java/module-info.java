module simplewebserver.cli {
    requires commons.cli;
    requires java.logging;
    requires hibegin.simplewebserver;
    exports com.hibegin.http.file.server;
    exports com.hibegin.http.file.config;
    exports com.hibegin.http.file.intercepter;
}