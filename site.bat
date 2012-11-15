@ECHO OFF

CD %~dp0
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_24
set MAVEN_OPTS=-Xmx768m
CALL mvn site
CALL mvn site:deploy
PAUSE