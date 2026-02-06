@echo off
cd %~dp0
java -jar obfuscator.jar "Sakura-1.1.jar_obf.jar" "M4h1r0-1.1_release.jar" -a -p "hotspot"
pause