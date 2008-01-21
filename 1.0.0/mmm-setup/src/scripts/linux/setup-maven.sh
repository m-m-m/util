#!/usr/bin/env bash
#$Id$

cd `dirname "${0}"`
. include.inc

M2_DIR=~/.m2
M2_SETTINGS=${M2_DIR}/settings.xml
if [ ! -e "${M2_SETTINGS}" ]
then
  echo "Copying maven configuration template to ${M2_SETTINGS}..."
  echo "ATTENTION: If you need a proxy server to access the internet modify it now!"
  mkdir -p "${M2_DIR}"
  cp ../../settings/maven/settings.xml "${M2_DIR}"
else
  echo "Maven configuration already available..."
fi
