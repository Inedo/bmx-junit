@echo off
CLS
SETLOCAL
SET PATH=%PATH%;c:\Program Files\Java\jdk1.6.0_12\bin\


REM ***************************************************************
REM * First, make sure our Unit Test Action actually works (out_test)
REM ***************************************************************
IF EXIST out_test (
  DEL out_test\*.* /s/q >nul
  RD out_test /s /q >nul
)
MD out_test

REM *** Compile everything and dump in out_test
javac -Xlint:unchecked -extdirs .\lib -d out_test src\jUnitAction.java src\DummyTest.java src\Dummy2Test.java src\DummyNotATest.java
java -Djava.ext.dirs=lib -cp out_test inedo.buildmasterextensions.java.jUnitAction @files.txt


REM ***************************************************************
REM * Second, compile the Unit Test Action (out)
REM ***************************************************************
IF EXIST out (
  DEL out\*.* /s/q >nul
  RD out /s /q >nul
)
MD out

REM *** Compile only the action
javac -Xlint:unchecked -extdirs .\lib -d out src\jUnitAction.java


REM *** jar it up
IF EXIST BuildMAster.jar DEL buildMaster.jar
jar cvf BuildMaster.jar -C out .

:END