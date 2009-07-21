@ECHO OFF
REM $Id$

CD %~dp0
CALL include.bat

IF EXIST "%ECLIPSE_WORKSPACE%" (
  ECHO Setting up eclipse projects...
  CD %MMM_BASE%
  CALL mvn "-Declipse.workspace=%ECLIPSE_WORKSPACE%" eclipse:add-maven-repo 
  CALL mvn "-Declipse.workspace=%ECLIPSE_WORKSPACE%" eclipse:eclipse -DdownloadSources=true && echo Eclipse successfully setup for mmm.
) ELSE (
  ECHO Eclipse workspace does NOT exist!
  ECHO Please launch eclipse once before:
  ECHO eclipse -data %ECLIPSE_WORKSPACE%
)
