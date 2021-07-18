module simplewebserver.cli {
    requires java.logging;
    requires hibegin.simplewebserver;
    requires fr.cril.cli;

    exports com.hibegin.http.file.intercepter to hibegin.simplewebserver;
    opens com.hibegin.http.file.server to fr.cril.cli;
}