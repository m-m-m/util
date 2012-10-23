@ECHO OFF

CD %~dp0
set MAVEN_OPTS=-Xmx1024m
CALL mvn site
CALL mvn site:deploy
PAUSE