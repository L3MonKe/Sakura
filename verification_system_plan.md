# 模组验证系统开发计划

本计划旨在为模组添加一个启动时的验证窗口，强制用户输入正确的用户名和密码后才能进入游戏主界面。

## 1. 核心目标
- 在游戏启动进入主菜单前拦截，显示验证窗口。
- 验证窗口包含用户名和密码输入框。
- 验证逻辑：用户名为 `MZC8865`，密码为 `886578`。
- 验证通过后进入主菜单；验证失败提示错误或无法进入。

## 2. 新增文件

### 2.1 `VerificationScreen.java`
- **路径**: `src/main/java/dev/mzc/client/gui/mainmenu/VerificationScreen.java`
- **功能**:
    - 继承自 `Screen` 类。
    - **UI组件**:
        - 用户名输入框 (`SakuraTextField`)
        - 密码输入框 (`SakuraTextField`，设置 `setPasswordMode(true)`)
        - 登录按钮 (`SakuraButton`)
        - 退出按钮 (可选，用于关闭游戏)
    - **状态管理**:
        - `public static boolean isVerified = false;` (静态标志位，记录验证状态)
    - **逻辑**:
        - 在 `init()` 中初始化 UI 组件。
        - 在 `render()` 中绘制背景（可复用 `MainMenuScreen` 的背景或模糊效果）和组件。
        - 点击登录按钮时，比对输入内容：
            - 若成功：设置 `isVerified = true`，并跳转 `mc.setScreen(new MainMenuScreen())`。
            - 若失败：显示红色错误提示文本。
        - 禁用 `ESC` 关闭屏幕，强制要求验证。

## 3. 修改文件

### 3.1 `MixinTitleScreen.java`
- **路径**: `src/main/java/dev/mzc/client/mixin/render/MixinTitleScreen.java`
- **修改逻辑**:
    - 在注入 `init` 方法时，检查 `VerificationScreen.isVerified`。
    - 如果未验证 (`!isVerified`)，则跳转到 `VerificationScreen`。
    - 如果已验证，保持原有逻辑（跳转 `MainMenuScreen`）。

### 3.2 `MixinSplashOverlay.java`
- **路径**: `src/main/java/dev/mzc/client/mixin/render/MixinSplashOverlay.java`
- **修改逻辑**:
    - 在加载完成准备进入主菜单时，同样检查 `VerificationScreen.isVerified`。
    - 优先跳转 `VerificationScreen`。

## 4. 开发步骤

1.  **创建 UI 界面 (`VerificationScreen`)**:
    - 参考 `AccountAddAccountScreen` 的布局和 `SakuraTextField` 的使用方法。
    - 实现静态验证状态标志。
    - 实现登录校验逻辑。

2.  **拦截启动流程**:
    - 修改 `MixinTitleScreen` 和 `MixinSplashOverlay`，接入验证状态判断。

3.  **验证与测试**:
    - 启动客户端，确认是否首先显示验证窗口。
    - 测试错误账号密码，确认无法进入。
    - 测试正确账号密码 (`MZC8865` / `886578`)，确认正常进入主菜单。

## 5. 待办事项 (Todo)
- [ ] 创建 `VerificationScreen.java`
- [ ] 修改 `MixinTitleScreen.java`
- [ ] 修改 `MixinSplashOverlay.java`
- [ ] 编译运行并测试验证流程
