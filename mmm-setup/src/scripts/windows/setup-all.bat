@ECHO OFF
REM $Id$

CD %~dp0
CALL setup-maven.bat
CALL setup-subversion.bat
CALL setup-eclipse.bat
