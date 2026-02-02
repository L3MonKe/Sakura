@echo off
cd %~dp0
java -jar obfuscator.jar "C:\Users\36797\Desktop\zenith-nightly-2.0.1_obf.jar" "C:\Users\36797\Desktop\zenith-nightly-2.0.1_release.jar" -a -p "hotspot"
pause