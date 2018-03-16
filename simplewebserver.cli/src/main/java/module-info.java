module simplewebserver.cli {
    requires java.logging;
    requires hibegin.simplewebserver;

    exports com.hibegin.http.file.intercepter to hibegin.simplewebserver;
}