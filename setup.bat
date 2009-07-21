@ECHO OFF
REM $Id$

SET PWD=%~dp0
CD %PWD%
CALL mmm-setup/src/scripts/windows/setup-all.bat
CD %PWD%
PAUSE