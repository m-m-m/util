@echo off
rem $Id: $

set PWD=%~dp0
cd %PWD%
call mmm-setup/src/scripts/windows/setup-all.bat
cd %PWD%
echo EW=%ECLIPSE_WORKSPACE%
pause