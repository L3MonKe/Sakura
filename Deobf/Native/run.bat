@echo off
cd %~dp0
java -jar obfuscator.jar "M4h1r0-1.0.1_obf.jar" "M4h1r0-1.0.1_release.jar" -a -p "hotspot"
pause