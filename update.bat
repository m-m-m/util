@ECHO OFF
REM $Id$

CD %~dp0
CALL mmm-setup/src/scripts/windows/setup-subversion.bat

CD %~dp0
CALL mvn "-Declipse.workspace=%ECLIPSE_WORKSPACE%" eclipse:eclipse
PAUSE