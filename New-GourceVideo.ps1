<#
.SYNOPSIS
    Gource GitHub 项目视频生成器 - 3分钟版
#>

param(
    [string]$OutputFile = "gource_video.mp4",
    [int]$Width = 1920,
    [int]$Height = 1080,
    [int]$TargetDuration = 264    # 🔥 4分24秒 = 264秒
)

# ==================== 配置 ====================
$AvatarDir = ".git/avatar"
$AutoSkip = 0.2
$UserScale = 2.5
$FontSize = 12
$BackgroundColor = "0a0a0a"
$FrameRate = 60
$CRF = 17
$Preset = "medium"

# ==================== 工具函数 ====================

function Write-Step {
    param([string]$Message, [string]$Color = "Cyan")
    Write-Host ""
    Write-Host "════════════════════════════════════════" -ForegroundColor DarkGray
    Write-Host "  $Message" -ForegroundColor $Color
    Write-Host "════════════════════════════════════════" -ForegroundColor DarkGray
}

function Download-Avatars {
    param([string]$AvatarDir)
    
    Write-Step "步骤 1: 下载 GitHub 头像（可选）" "Green"
    
    if (!(Test-Path $AvatarDir)) {
        New-Item -ItemType Directory -Path $AvatarDir -Force | Out-Null
    }
    
    $authors = git log --pretty=format:"%ae|%an" 2>$null | Select-Object -Unique
    $success = 0
    $failed = 0
    
    foreach ($line in $authors) {
        if ($line -notlike "*|*") { continue }
        
        $email, $name = $line -split '\|', 2
        $name = $name.Trim()
        if ([string]::IsNullOrWhiteSpace($name)) { continue }
        
        $safeName = $name -replace '[<>:"/\\|?*]', '_'
        $avatarPath = Join-Path $AvatarDir "$safeName.png"
        
        if (Test-Path $avatarPath) {
            $success++
            continue
        }
        
        # Gravatar URL（注意：中间不要有空格）
        $bytes = [System.Text.Encoding]::UTF8.GetBytes($email.Trim().ToLower())
        $hash = [System.Security.Cryptography.MD5]::Create().ComputeHash($bytes)
        $hashString = [System.BitConverter]::ToString($hash).Replace('-', '').ToLower()
        $url = "https://www.gravatar.com/avatar/$hashString?d=404&s=128"
        
        try {
            $response = Invoke-WebRequest -Uri $url -TimeoutSec 10 -UseBasicParsing
            if ($response.StatusCode -eq 200) {
                [System.IO.File]::WriteAllBytes($avatarPath, $response.Content)
                $success++
                Start-Sleep -Milliseconds 200
            } else {
                $failed++
            }
        } catch {
            $failed++
        }
    }
    
    Write-Host "  头像：成功 $success | 失败 $failed" -ForegroundColor $(if($success -gt 0){"Green"}else{"Yellow"})
    return $success -gt 0
}

function Generate-Video {
    param(
        [string]$OutputFile,
        [string]$AvatarDir,
        [int]$Width,
        [int]$Height,
        [double]$SecondsPerDay,
        [double]$AutoSkip
    )
    
    Write-Step "步骤 2: 生成高清视频" "Magenta"
    
    $OutputPath = Join-Path (Get-Location) $OutputFile
    $AvatarPath = Join-Path (Get-Location) $AvatarDir
    
    # 兼容的 Gource 参数
    $gourceArgs = "-${Width}x${Height} " +
        "-s $SecondsPerDay " +
        "-a $AutoSkip " +
        "--max-file-lag 0.2 " +
        "--file-idle-time 0 " +
        "--stop-at-end " +
        "--highlight-users " +
        "--hide mouse,progress,filenames " +
        "--user-image-dir `"$AvatarPath`" " +
        "--user-scale $UserScale " +
        "--font-size $FontSize " +
        "--background-colour $BackgroundColor " +
        "--output-ppm-stream - -o -"
    
    $ffmpegArgs = "-y -r $FrameRate -f image2pipe -vcodec ppm -i - " +
        "-vcodec libx264 -preset $Preset -pix_fmt yuv420p -crf $CRF -threads 0 " +
        "`"$OutputPath`""
    
    Write-Host "  📊 规格：${Width}x${Height} @ ${FrameRate}fps" -ForegroundColor Gray
    Write-Host "  🎨 效果：头像${UserScale}x | 字体${FontSize}px" -ForegroundColor Gray
    Write-Host "  ⏱️  目标时长：${TargetDuration}秒 (-s $SecondsPerDay)" -ForegroundColor Gray
    Write-Host ""
    Write-Host "  ⏳ 开始渲染，请勿关闭窗口..." -ForegroundColor Yellow
    Write-Host ""
    
    $startTime = Get-Date
    cmd /c "gource $gourceArgs | ffmpeg $ffmpegArgs"
    $endTime = Get-Date
    $renderTime = ($endTime - $startTime).TotalSeconds
    
    Write-Host ""
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  ✅ 视频生成成功！" -ForegroundColor Green
        Write-Host "  📁 位置：$OutputPath" -ForegroundColor Cyan
        
        try {
            $duration = ffprobe -v quiet -show_entries format=duration -of csv=p=0 "$OutputPath" 2>$null
            if ($duration) {
                Write-Host "  🎬 实际时长：$([math]::Round($duration, 1)) 秒" -ForegroundColor Cyan
            }
        } catch {}
        
        return $true
    } else {
        Write-Error "  ❌ 失败，退出码：$LASTEXITCODE"
        return $false
    }
}

# ==================== 主程序 ====================

Write-Host ""
Write-Host "╔═══════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║      Gource GitHub 项目视频生成器 (3分钟版)                ║" -ForegroundColor Cyan
Write-Host "╚═══════════════════════════════════════════════════════════╝" -ForegroundColor Cyan

# 检查依赖
Write-Step "检查依赖" "Blue"
foreach ($cmd in @("git", "gource", "ffmpeg")) {
    if (Get-Command $cmd -ErrorAction SilentlyContinue) {
        Write-Host "  ✅ $cmd" -ForegroundColor Green
    } else {
        Write-Host "  ❌ $cmd 未安装" -ForegroundColor Red
        exit 1
    }
}

# 检查 Git 仓库
if (!(Test-Path ".git")) {
    Write-Error "❌ 当前目录不是 Git 仓库"
    exit 1
}

# 获取仓库信息
$commitCount = git rev-list --count HEAD 2>$null
$firstCommit = git log --reverse --pretty=format:"%ad" --date=short 2>$null | Select-Object -First 1
$lastCommit = git log --pretty=format:"%ad" --date=short 2>$null | Select-Object -First 1

$days = 30
if ($firstCommit -and $lastCommit) {
    $days = ([DateTime]$lastCommit - [DateTime]$firstCommit).Days
    if ($days -lt 1) { $days = 1 }
}

Write-Host "  📊 Commits: $commitCount" -ForegroundColor Gray
Write-Host "  📅 跨度：$firstCommit ~ $lastCommit ($days 天)" -ForegroundColor Gray

# 🔥 计算时间缩放（目标时长 180 秒）
$SecondsPerDay = [math]::Round(($TargetDuration - 10) / $days, 2)
if ($SecondsPerDay -lt 0.05) { $SecondsPerDay = 0.05 }
Write-Host "  ⏱️  自动计算 -s 参数：$SecondsPerDay" -ForegroundColor Gray

# 下载头像
Download-Avatars -AvatarDir $AvatarDir

# 生成视频
$success = Generate-Video `
    -OutputFile $OutputFile `
    -AvatarDir $AvatarDir `
    -Width $Width `
    -Height $Height `
    -SecondsPerDay $SecondsPerDay `
    -AutoSkip $AutoSkip

Write-Host ""
if ($success) {
    Write-Host "╔═══════════════════════════════════════════════════════════╗" -ForegroundColor Green
    Write-Host "║                    ✅ 完成！                              ║" -ForegroundColor Green
    Write-Host "╚═══════════════════════════════════════════════════════════╝" -ForegroundColor Green
    Write-Host "🎉 $(Join-Path (Get-Location) $OutputFile)" -ForegroundColor Cyan
} else {
    Write-Host "╔═══════════════════════════════════════════════════════════╗" -ForegroundColor Red
    Write-Host "║                    ❌ 失败                                ║" -ForegroundColor Red
    Write-Host "╚═══════════════════════════════════════════════════════════╝" -ForegroundColor Red
    exit 1
}