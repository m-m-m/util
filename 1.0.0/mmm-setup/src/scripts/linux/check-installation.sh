#!/usr/bin/env bash
#$Id$

cd `dirname "${0}"`
. include.inc

# checks if the given comand is available
function f_requireCmd() {
  cmd=${1}
  prg=`which ${1}`
  echo "### checking ${1} ###"
  if [ -x "${prg}" ]
  then
    echo "Found ${cmd} at ${prg}"
    ${prg} ${2}
    echo
  else
    echo "Could not find ${cmd} in your path:"
    echo $PATH
    echo "Please install ${cmd} and add it to your path..."
    exit 1
  fi
}

f_requireCmd java -version
JAVA_VERSION=`java -version 2>&1 | grep 'java version'`
V=${JAVA_VERSION:14:3}
if [ "${V}" = "1.5" ] || [ "${V}" = "1.6" ]
then
  echo "Found java version ${V} which is okay"
  echo
else
  echo "Your java version must be 1.5 or 1.6 - found ${V}"
  echo "Please install a jdk in version 1.5 or 1.6 and ensure it is firt java in your path"
  exit 1
fi
f_requireCmd mvn -version
if [ ! -e "~/.m2/settings.xml" ]
then
  echo "Copying maven configuration template to ~/.m2/settings.xml"
  echo "ATTENTION: If you need a proxy server to reach the internet please modify this file now!"
  mkdir -p "~/.m2/"
  cp ../../settings/maven/settings.xml "~/.m2/"
else
  echo "Maven configuration already available..."
fi
#f_requireCmd svn --version

echo "Your installation seems to be okay."
