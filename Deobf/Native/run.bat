@echo off
cd %~dp0
java -jar obfuscator.jar "D:\GitHub\Sakura\Deobf\Grunt\obfuscated\M4h1r0-1.0.1_obf.jar" "D:\GitHub\Sakura\Deobf\Native\M4h1r0-1.0.1_release.jar" -a -p "hotspot"
pause