@echo off
cd %~dp0
java -jar obfuscator.jar "Sakura-1.4_obf.jar" "Sakura-1.4_release.jar" -a -p "hotspot"
pause