@ECHO OFF
REM $Id$

CD %~dp0
CALL mmm-setup/src/scripts/windows/include.bat

CD %~dp0
CALL mvn install
PAUSE