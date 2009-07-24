@ECHO OFF
REM $Id$

CD %~dp0
CALL include.bat

ECHO checking subversion configuration...
SET SVN_DIR=%APPDATA%\Subversion
IF NOT EXIST "%SVN_DIR%" (
  MD "%SVN_DIR%"
)
IF NOT EXIST "%SVN_DIR%\_mmm-stop_" (
  IF EXIST "%SVN_DIR%\config" (
    IF NOT EXIST "%SVN_DIR%\config.bak.mmm" (
      ECHO creating backup of original subversion config.
      COPY "%SVN_DIR%\config" "%SVN_DIR%\config.bak.mmm"
    )
  )
  ECHO copying latest subversion config
  COPY "..\..\settings\subversion\config" "%SVN_DIR%"
)
