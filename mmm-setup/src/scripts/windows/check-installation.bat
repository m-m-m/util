rem $Id$

cd %~dp0
call include.bat

java -version 2>&1 | find "java version"
rem JAVA_VERSION=`java -version 2>&1 | grep 'java version'`
rem V=${JAVA_VERSION:14:3}
rem if [ "${V}" = "1.5" ] || [ "${V}" = "1.6" ]
rem then
rem   echo "Found java version ${V} which is okay"
rem   echo
rem else
rem   echo "Your java version must be 1.5 or 1.6 - found ${V}"
rem   echo "Please install a jdk in version 1.5 or 1.6 and ensure it is firt java in your path"
rem   exit 1
rem fi
mvn -version

rem f_requireCmd mvn -version
if not %ERRORLEVEL% == 0 (
  echo Maven2 is NOT installed property.
  echo Please install maven2 and make it available in your PATH variable.
)
rem if [ ! -e "~/.m2/settings.xml" ]
rem then
rem   echo "Copying maven configuration template to ~/.m2/settings.xml"
rem   echo "ATTENTION: If you need a proxy server to reach the internet please modify this file now!"
rem   mkdir -p "~/.m2/"
rem   cp ../../settings/maven/settings.xml "~/.m2/"
rem else
rem   echo "Maven configuration already available..."
rem fi
rem #f_requireCmd svn --version
rem 
rem echo "Your installation seems to be okay."
