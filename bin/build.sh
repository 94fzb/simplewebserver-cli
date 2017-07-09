#!/usr/bin/env bash
mvn clean package
cp bin/simplewebserver.sh simplewebserver.sh
chmod a+x simplewebserver.sh
zip -r jlink/target/*.zip simplewebserver.sh
cp jlink/target/*.zip  simplewebserver.cli-linx-x64-0.0.6.zip
rm simplewebserver.sh