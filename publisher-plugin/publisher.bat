@ECHO OFF

set _JAVACMD=java.exe
if exist thirdparty\jdk\bin\java.exe set _JAVACMD=thirdparty\jdk\bin\java.exe
if exist ..\jdk\bin\java.exe set _JAVACMD=..\jdk\bin\java.exe
if exist "%JAVA_HOME%\bin\java.exe" set _JAVACMD=%JAVA_HOME%\bin\java.exe



"%_JAVACMD%" -Xms32M -Xmx1024M -jar publisher-plugin.jar %1 %2 %3 %4 %5 %6 %7 %8 %9

@ECHO ON