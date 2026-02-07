@echo off
cd %~dp0
java -jar obfuscator.jar "Sakura-1.1.1.jar_obf.jar" "Sakura-1.1.1.jar_release.jar" -a -p "hotspot"
pause