rem $Id$

cd %~dp0
call include.bat

set M2_DIR=%USERPROFILE%\.m2
set M2_SETTINGS=%M2_DIR%\settings.xml
if not exist "%M2_SETTINGS%" (
  echo Copying maven configuration template to %M2_SETTINGS%...
  echo ATTENTION: If you need a proxy server to access the internet modify it now!
  md "%M2_DIR%"
  copy "..\..\settings\maven\settings.xml" "%M2_DIR%"
) else (
  echo Maven configuration already available...
)
