@ECHO OFF
REM $Id$

CD %~dp0
CALL include.bat

ECHO checking java installation...
java -version 2>&1 || (ECHO Java is NOT properly installed!
PAUSE
GOTO:EOF
)
java -version 2>&1 | FIND "java version" | find "1.6">nul
IF %ERRORLEVEL% NEQ 0 (
  REM NOT java 6
  java -version 2>&1 | FIND "java version" | find "1.7">nul
  IF %ERRORLEVEL% NEQ 0 (
    ECHO Wrong Java version! Has to be at least 1.6!
    PAUSE
    GOTO:EOF
  )
)
ECHO OK
ECHO .

ECHO checking maven installation...
call mvn -version || (ECHO Maven is NOT properly installed!
PAUSE
GOTO:EOF
)
ECHO OK
ECHO .

REM ECHO checking svn installation...
REM svn -version || (ECHO Subversion is NOT properly installed!
REM PAUSE
REM GOTO:EOF
REM )

ECHO Your installation seems to be okay.
