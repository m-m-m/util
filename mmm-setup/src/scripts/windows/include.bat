@ECHO OFF
REM $Id$

CD %~dp0
REM This file is included to activate the custom settings

SET MMM_BASE=..\..\..\..
IF EXIST settings.bat (
  CALL settings.bat
) ELSE (
  COPY settings.bat.template settings.bat
  ECHO Welcome to the Multi-Media-Manager project!
  ECHO This seems to be the first time you invoked a setup script.
  ECHO The following file has been created for you:
  ECHO %~dp0\settings.bat
  ECHO Please edit the file and make your individual changes as needed.
  ECHO Now checking your installation...
  ECHO If this fails restart %~dp0\check-installation.bat after fixing your settings.bat
  CALL check-installation.bat
)
