@ECHO OFF
REM $Id$

cd %~dp0
CALL mmm-setup/src/scripts/windows/setup-all.bat

CD %~dp0
CALL mvn -Dmaven.test.skip=true install
PAUSE