IF not exist zip (mkdir zip)
mvn clean package && copy  bin\simplewebserver.bat jlink\target\jlink\simplewebserver.bat && cd jlink\target\ && jar -cMf simplewebserver.cli-windows-0.0.6.zip  -C jlink . && copy simplewebserver.cli-windows-0.0.6.zip ..\..\zip\
exit