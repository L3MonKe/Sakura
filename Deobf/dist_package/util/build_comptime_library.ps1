# Get the absolute path of the script directory
$cdir = $PSScriptRoot

# HARDCODED COMPILER PATH
$zigCompiler = "C:\Users\jiuxian_baka\Documents\zig-x86_64-windows-0.15.2\zig.exe"

# Define the output directory and create it if it doesn't exist
$outDir = Join-Path $cdir "..\natives"
if (!(Test-Path $outDir)) {
    Write-Host "Creating output directory: $outDir"
    New-Item -ItemType Directory -Path $outDir -Force | Out-Null
}

# Check if arguments are provided
if ($args.Count -eq 0) {
    $targets = @(
        "x86_64-linux-gnu", "libnativeUtils.so",
        "x86_64-windows-gnu", "nativeUtils.dll",
        "x86_64-macos-none", "libnativeUtils.dylib"
    )
} else {
    $targets = $args
}

Push-Location $cdir

try {
    for ($i = 0; $i -lt $targets.Count; $i += 2) {
        $nt = $targets[$i]
        if (($i + 1) -ge $targets.Count) { break }
        $name = $targets[$i + 1]

        Write-Host "building $nt into $name"

        $outPath = Join-Path $outDir $name

        & $zigCompiler c++ -Xpreprocessor -DCOMPILING_COMPTIME_LIB -shared -O2 -fno-exceptions `
            -o "$outPath" `
            -I "$cdir" `
            -std=c++23 "-Wl,-S" `
            chacha20.cpp `
            -target "$nt"
    }
}
finally {
    Pop-Location
}

# Remove .lib files
Remove-Item -Path "$outDir\*.lib" -Verbose -ErrorAction SilentlyContinue