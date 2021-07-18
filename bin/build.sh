#!/usr/bin/env bash
rm -rf zip
mkdir zip
./mvnw clean package
cp bin/simplewebserver.sh simplewebserver.sh
chmod a+x simplewebserver.sh
zip -r jlink/target/*.zip simplewebserver.sh
cp jlink/target/jlink*.zip zip/simplewebserver.cli-linux-x64-0.0.6.zip
rm simplewebserver.sh