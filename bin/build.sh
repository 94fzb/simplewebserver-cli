#!/usr/bin/env bash
mvn clean package
cp bin/simplewebserver.sh simplewebserver.sh
chmod a+x simplewebserver.sh
zip -r jlink/target/*.zip simplewebserver.sh
rm simplewebserver.sh