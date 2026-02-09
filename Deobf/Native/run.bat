@echo off
cd %~dp0
java -jar obfuscator.jar "Sakura-1.3-Windows.jar" "Sakura-1.3-Windows_release.jar" -a -p "hotspot"
pause