rem $Id$

rem This file is included to activate the custom settings

set MMM_BASE=..\..\..\..
if exist settings.bat (
  call settings.bat
) else (
  copy settings.bat.template settings.bat
  echo Welcome to the Multi-Media-Manager project!
  echo This seems to be the first time you invoked a setup script.
  echo The following file has been created for you:
  echo %~dp0\settings.bat
  echo Please edit the file and make your individual changes as needed.
  echo Now checking your installation...
  echo If this fails restart %~dp0\check-installation.bat after fixing your settings.bat
  call check-installation.bat
  rem exit ${?}
)
