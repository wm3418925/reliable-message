@echo off
cd %~dp0

set APP_HOME=%~dp0

set CONF_FILE=%APP_HOME%setenv.bat
rem check CONF_FILE exist
if not exist "%CONF_FILE%" (
   echo "%APP_HOME% is not exist %CONF_FILE%  file, please check it!"
   exit 1
)
rem read CONF_FILE
call %CONF_FILE%

echo %APP_MAINCLASS%



rem check JAVA_HOME
if not defined JAVA_HOME (
	echo "%CONF_FILE%  file  is not set JAVA_HOME variable, please set it"
	exit
)
echo "JAVA_HOME is %JAVA_HOME%!"

rem check APP_MAINCLASS
if not defined APP_MAINCLASS (
    echo "%CONF_FILE%  file  is not set APP_MAINCLASS variable, please set it!"
	echo ""
	exit 1
)
echo "APP_MAINCLASS is %APP_MAINCLASS%!"

rem check LOG_HOME
if not defined LOG_HOME (
    echo "%CONF_FILE%  file  is not set LOG_HOME variable, please set it!"
	exit 1
)
echo "LOG_HOME is %LOG_HOME%!"

rem check JAVA_OPTS
if not defined JAVA_OPTS (
    echo "%CONF_FILE%  file  is not set JAVA_OPTS variable, please set it!"
	exit 1
)
echo "JAVA_OPTS is %JAVA_OPTS%!"


set STDOUT_LOG=%LOG_HOME%\STDOUT.log
mkdir %LOG_HOME%
rem check STDOUT_LOG exist
rem if not exist "%STDOUT_LOG%" (
rem touch %STDOUT_LOG%
rem )

rem .....classpath.......lib......jar
setlocal enabledelayedexpansion
set CLASSPATH=
for /f "delims=" %%i in ('dir %APP_HOME%lib\* /a /b /s') do (
	set BL1=%%i
	set CLASSPATH=!BL1!;!CLASSPATH!
)
set CLASSPATH=%APP_HOME%;%APP_HOME%lib;%APP_HOME%config;%APP_HOME%config\log4j.properties;!CLASSPATH!

echo "CLASSPATH=%CLASSPATH%"


rem ##################################
rem (..)....
rem 
rem ...
rem 1. ....checkpid.....$psid....
rem 2. .........$psid...0..........
rem 3. ..................
rem 4. ............checkpid..
rem 5. ....4..........pid,...[OK].....[Failed]
rem ...echo -n ...........
rem ..: "nohup ... >\dev\null 2>&1 &" ...
rem ##################################

echo "Starting %APP_MAINCLASS% ..."
"%JAVA_HOME%\bin\java" %JAVA_OPTS% -classpath %CLASSPATH% %APP_MAINCLASS% >>%STDOUT_LOG% 2>&1

pause
exit
