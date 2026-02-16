@echo off
cd /d "%¡«dp0"
cls
echo ========================================
echo Native Library Build and Integration Tool
echo Project Path: %cd%
echo ========================================
echo.

echo [1/5] Cleaning build directory...
if exist build rmdir /s /q build
mkdir build
cd build

echo [2/5] Configuring CMake...
cmake .. -G "Visual Studio 17 2022" -A x64
if errorlevel 1 (
    echo.
    echo ERROR: CMake configuration failed!
    echo Check: CMakeLists.txt exists, VS2022 installed
    goto end
)
echo [OK] CMake configured

echo [3/5] Building Release...
cmake --build . --config Release -- /m
if errorlevel 1 (
    echo.
    echo ERROR: Build failed!
    goto end
)
cd ..
echo [OK] Build completed

echo [4/5] Verifying DLL...
if not exist "build\build\lib\native_library.dll" (
    echo.
    echo ERROR: DLL not found at build\build\lib\native_library.dll
    goto end
)
echo [OK] DLL verified

echo [5/5] Integrating into JAR as x64-windows.dll...
set "JAR=..\Sakura-1.5_obf.jar"
if not exist "%JAR%" (
    echo.
    echo ERROR: Target JAR not found: %JAR%
    goto end
)
copy /y "%JAR%" "%JAR%.bak" >nul
set "TEMP_DIR=_temp_pack"
if exist "%TEMP_DIR%" rmdir /s /q "%TEMP_DIR%"
mkdir "%TEMP_DIR%\native0"
copy /y "build\build\lib\native_library.dll" "%TEMP_DIR%\native0\x64-windows.dll" >nul

where 7z >nul 2>&1 && (
    cd "%TEMP_DIR%"
    7z a "..\%JAR%" "native0" -tzip -mx=5 -y >nul
    cd ..
) || (
    where jar >nul 2>&1 && (
        cd "%TEMP_DIR%"
        jar uf "..\%JAR%" -C . "native0" >nul
        cd ..
    ) || (
        echo.
        echo ERROR: Need 7-Zip or JDK installed and in PATH
        rmdir /s /q "%TEMP_DIR%"
        goto end
    )
)

if errorlevel 1 (
    echo.
    echo ERROR: Integration failed! Restoring backup...
    copy /y "%JAR%.bak" "%JAR%" >nul
    rmdir /s /q "%TEMP_DIR%"
    goto end
)

rmdir /s /q "%TEMP_DIR%"
echo.
echo ========================================
echo SUCCESS! DLL integrated as native0/x64-windows.dll
echo Backup saved: %JAR%.bak
echo ========================================

:end
echo.
echo Press ANY KEY to close this window...
pause >nul