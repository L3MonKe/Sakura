@echo off
cd %~dp0
java -jar obfuscator.jar "Sakura-1.5_obf.jar" "Sakura-1.5_release.jar" -a -p "hotspot"
pause