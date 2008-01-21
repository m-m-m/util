#!/usr/bin/env bash
# $Id$

cd `dirname "${0}"`
. include.inc
cd ${MMM_BASE}
mvn -Declipse.workspace=${ECLIPSE_WORKSPACE} eclipse:add-maven-repo 
mvn eclipse:eclipse

echo "Eclipse successfully setup for mmm."
