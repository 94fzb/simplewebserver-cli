module simplewebserver.cli {
    requires java.logging;
    requires hibegin.simplewebserver;
    requires fr.cril.cli;

    exports com.hibegin.http.file.intercepter to hibegin.simplewebserver;
    exports com.hibegin.http.file.server;
    opens com.hibegin.http.file.server to fr.cril.cli;
}