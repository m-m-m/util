@echo off
REM $Id$

CD %~dp0
CALL include.bat

ECHO checking java installation...
java -version 2>&1 || (ECHO Java is NOT properly installed!
PAUSE
GOTO:EOF
)
java -version 2>&1 | FIND "java version" | find "1.5">nul
IF %ERRORLEVEL% NEQ 0 (
  REM NOT java 5
  java -version 2>&1 | FIND "java version" | find "1.6">nul
  IF %ERRORLEVEL% NEQ 0 (
    REM NOT java 6
    java -version 2>&1 | FIND "java version" | find "1.7">nul
    IF %ERRORLEVEL% NEQ 0 (
      ECHO Wrong Java version! Has to be at least 1.5!
      PAUSE
      GOTO:EOF
    )
  )
)
ECHO OK
ECHO .

ECHO checking maven installation...
call mvn -version || (ECHO Maven is NOT properly installed!
PAUSE
GOTO:EOF
)
echo checking maven configuration...
IF NOT EXIST "%USERPROFILE%\.m2" (
  MD "%USERPROFILE%\.m2"
)
IF EXIST "%USERPROFILE%\.m2\settings.xml" (
  ECHO Maven configuration already available at %USERPROFILE%\.m2\settings.xml...
) ELSE (
  ECHO Copying maven configuration template to %USERPROFILE%/.m2/settings.xml
  COPY "..\..\settings\maven\settings.xml" "%USERPROFILE%\.m2\"
  ECHO ATTENTION: If you need a proxy server to reach the internet please modify this file now!
)
ECHO OK
ECHO .

REM ECHO checking svn installation...
REM svn -version || (ECHO Subversion is NOT properly installed!
REM PAUSE
REM GOTO:EOF
REM )

ECHO Your installation seems to be okay.
