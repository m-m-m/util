@ECHO OFF
REM $Id$

CD %~dp0
CALL include.bat

SET M2_DIR=%USERPROFILE%\.m2
IF NOT EXIST "%M2_DIR%" (
  MD "%M2_DIR%"
)
SET M2_SETTINGS=%M2_DIR%\settings.xml
IF NOT EXIST "%M2_SETTINGS%" (
  ECHO Copying maven configuration template to %M2_SETTINGS%...
  ECHO ATTENTION: If you need a proxy server to access the internet modify it now!
  COPY "..\..\settings\maven\settings.xml" "%M2_DIR%"
)
