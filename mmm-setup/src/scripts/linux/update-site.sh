#!/usr/bin/env bash

cd `dirname "${0}"`
. include.inc
cd ${MMM_BASE}
pwd
mvn site:stage -DstagingDirectory=${PWD}/target/stage/ | tee .maven-site.log
