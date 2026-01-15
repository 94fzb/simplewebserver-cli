#!/usr/bin/env bash
basePath=${1}
mkdir -p ${basePath}
echo "real target folder ${basePath}"

java -version
sh bin/build-info.sh
./mvnw ${2} clean package
./mvnw ${2} -Pnative -Dagent exec:exec@java-agent -U
./mvnw ${2} -Pnative package
binName="simplewebserver-cli"
targetFile=""
sourceFile=""
if [ -f "target/${binName}.exe" ];
then
  echo "window"
  sourceFile="target/${binName}.exe"
  targetFile="${basePath}/${binName}-Windows-$(uname -m).exe"
  choco install upx
  mv ${sourceFile} ${targetFile}
  upx --best ${targetFile}
fi
if [[ "$(uname -s)" == "Linux" ]];
then
  echo "Linux"
  sourceFile="target/${binName}"
  targetFile="${basePath}/${binName}-$(uname -s)-$(dpkg --print-architecture).bin"
  sudo apt install upx -y
  mv ${sourceFile} ${targetFile}
  upx --best ${targetFile}
else
  echo "MacOS"
  sourceFile="target/${binName}"
  targetFile="${basePath}/${binName}-$(uname -s)-$(uname -m).bin"
  brew install upx
  mv ${sourceFile} ${targetFile}
#  upx --best ${targetFile}
fi
