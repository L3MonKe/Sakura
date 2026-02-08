@echo off
cd %~dp0
java -jar obfuscator.jar "Sakura-1.2-Windows.jar" "Sakura-1.2-Windows_release.jar" -a -p "hotspot"
pause