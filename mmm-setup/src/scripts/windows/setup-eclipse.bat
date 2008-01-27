rem $Id$

cd %~dp0
call include.bat
if exist %ECLIPSE_WORKSPACE% (
  echo Setting up eclipse projects...
  cd %MMM_BASE%
  call mvn -Declipse.workspace=%ECLIPSE_WORKSPACE% eclipse:add-maven-repo 
  call mvn eclipse:eclipse && echo Eclipse successfully setup for mmm.
) else (
  echo Eclipse workspace does NOT exist. Please launch eclipse once before
  echo you call this script:
  echo eclipse -data %ECLIPSE_WORKSPACE%
)
