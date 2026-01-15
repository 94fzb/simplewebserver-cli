# SimpleWebServer-cli

### dev package

```shell
export JAVA_HOME=${HOME}/dev/graalvm-jdk-latest
export PATH=${JAVA_HOME}/bin:$PATH
```

### Download

- Linux
  - [x86](https://dl.hibegin.com/sws-cli/simplewebserver-cli-Linux-amd64.bin)
  - [arm64](https://dl.hibegin.com/sws-cli/simplewebserver-cli-Linux-arm64.bin)
- Mac
  - [x86](https://dl.hibegin.com/sws-cli/simplewebserver-cli-Darwin-x86_64.bin)
  - [arm64](https://dl.hibegin.com/sws-cli/simplewebserver-cli-Darwin-arm64.bin)
- Windows
  - [x86](https://dl.hibegin.com/sws-cli/simplewebserver-cli-Windows-x86_64.exe)


#### Linux amd64

```
sudo wget https://dl.hibegin.com/sws-cli/simplewebserver-cli-Linux-amd64.bin -O /usr/local/bin/simplewebserver-cli && sudo chmod a+x /usr/local/bin/simplewebserver-cli  
```

#### Linux arm64

```
sudo wget https://dl.hibegin.com/sws-cli/simplewebserver-cli-Linux-arm64.bin -O /usr/local/bin/simplewebserver-cli && sudo chmod a+x /usr/local/bin/simplewebserver-cli  
```

#### MacOS x86

```
sudo wget https://dl.hibegin.com/sws-cli/simplewebserver-cli-Darwin-x86_64.bin -O /usr/local/bin/simplewebserver-cli && sudo chmod a+x /usr/local/bin/simplewebserver-cli  
```

#### MacOS arm64

```
sudo wget https://dl.hibegin.com/sws-cli/simplewebserver-cli-Darwin-arm64.bin -O /usr/local/bin/simplewebserver-cli && sudo chmod a+x /usr/local/bin/simplewebserver-cli  
```


### Usage

simplewebserver-cli --help

```
usage: --help
 -f,--path <arg>        base path (default current)
 -h,--help              print help message
 -i,--autoIndex <arg>   auto index render file folder,
                        0 is disable, other value enable (default enable)
 -l,--location <arg>    base uri (default /)
 -p,--port <arg>        http file server listener port (default port 7080)
 -v,--version           print simplewebserver-cli version
```
