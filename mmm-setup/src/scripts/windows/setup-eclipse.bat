rem $Id$

cd %~dp0
call include.bat
if exists %ECLIPSE_WORKSPACE% (
  echo Setting up eclipse projects...
  cd %MMM_BASE%
  mvn -Declipse.workspace=%ECLIPSE_WORKSPACE% eclipse:add-maven-repo 
  mvn eclipse:eclipse && echo Eclipse successfully setup for mmm.
)

