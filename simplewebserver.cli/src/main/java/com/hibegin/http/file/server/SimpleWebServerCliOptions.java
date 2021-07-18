package com.hibegin.http.file.server;

import fr.cril.cli.annotations.Args;
import fr.cril.cli.annotations.Description;
import fr.cril.cli.annotations.LongName;
import fr.cril.cli.annotations.ShortName;

public class SimpleWebServerCliOptions {

    public static final int DEFAULT_PORT = 7080;

    @ShortName("p")
    @LongName("port")
    @Args(1)
    @Description("http file server listener port (default port " + DEFAULT_PORT + ")")
    private Integer port;

    @ShortName("f")
    @LongName("path")
    @Args(1)
    @Description("base path (default current)")
    private String path;

    @ShortName("v")
    @LongName("version")
    @Args(1)
    @Description("print version")
    private String version;

    @ShortName("h")
    @LongName("help")
    @Args(1)
    @Description("print help message")
    private String help;

    @ShortName("i")
    @LongName("autoIndex")
    @Args(1)
    @Description("auto index render file folder,0 is on, other value close")
    private Integer autoIndex;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Integer getAutoIndex() {
        return autoIndex;
    }

    public void setAutoIndex(Integer autoIndex) {
        this.autoIndex = autoIndex;
    }
}