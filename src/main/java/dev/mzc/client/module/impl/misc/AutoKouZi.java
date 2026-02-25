package dev.mzc.client.module.impl.misc;

import dev.mzc.client.config.ConfigManager;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.client.ChatUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.values.impl.StringValue;
import meteordevelopment.orbit.EventHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AutoKouZi extends Module {
    private enum FileMode {
        FuckYou("槽你妈"),
        AntiFuckYou("反槽你妈"),
        AntiHYW("反何意味"),
        Antijijiji("反你已急哭"),
        Auto("自动");

        private final String cnName;

        FileMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private enum SuffixMode {
        Letters("字母"),
        Numbers("数字"),
        Traditional("繁体"),
        Smart("智能");

        private final String cnName;

        SuffixMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private static final String KOUZI_FILE_NAME = "KouZi";
    private static final String KOUZI_FILE_NAME_TXT = "AntiKouZi";
    private static final String HYW_FILE_NAME = "AntiHYW.txt";
    private static final String JIJIJI_FILE_NAME = "Antijijiji.txt";
    private static final Path PRIMARY_FILE = ConfigManager.CONFIG_DIR.resolve(KOUZI_FILE_NAME);
    private static final Path PRIMARY_FILE_TXT = ConfigManager.CONFIG_DIR.resolve(KOUZI_FILE_NAME_TXT);
    private static final Path PRIMARY_FILE_HYW = ConfigManager.CONFIG_DIR.resolve(HYW_FILE_NAME);
    private static final Path PRIMARY_FILE_JIJIJI = ConfigManager.CONFIG_DIR.resolve(JIJIJI_FILE_NAME);
    private static final Path ALT_FILE = Paths.get("..").resolve(PRIMARY_FILE);
    private static final Path ALT_FILE_TXT = Paths.get("..").resolve(PRIMARY_FILE_TXT);
    private static final Path ALT_FILE_HYW = Paths.get("..").resolve(PRIMARY_FILE_HYW);
    private static final Path ALT_FILE_JIJIJI = Paths.get("..").resolve(PRIMARY_FILE_JIJIJI);

    private final NumberValue<Integer> delayTicks = new NumberValue<>("Delay", "延迟tick", 20, 1, 100, 1);
    private final EnumValue<FileMode> fileMode = new EnumValue<>("FileMode", "文件模式", FileMode.FuckYou, FileMode.class);
    private final BoolValue randomSuffix = new BoolValue("RandomSuffix", "随机后缀", false);
    private final EnumValue<SuffixMode> suffixMode = new EnumValue<>("SuffixMode", "后缀模式", SuffixMode.Letters, SuffixMode.class, randomSuffix::get);
    private final NumberValue<Integer> suffixLength = new NumberValue<>("SuffixLength", "后缀长度", 6, 1, 20, 1, randomSuffix::get);
    private final BoolValue whisperMode = new BoolValue("Whisper", "私聊模式", false);
    private final StringValue whisperTarget = new StringValue("WhisperTarget", "私聊玩家", "Player", whisperMode::get);

    private int tickCounter;
    private int lineIndex;
    private List<String> lines = List.of();
    private int reloadCounter;
    private Path activeFile;
    private long lastModified = -1L;

    public AutoKouZi() {
        super("AutoKouZi", "自动扣字", Category.Misc);
        this.setType(ModuleType.Safe);
    }

    @Override
    protected void onEnable() {
        tickCounter = 0;
        lineIndex = 0;
        reloadCounter = 0;
        loadLines();
        if (lines.isEmpty()) {
            ChatUtil.addChatMessage("错误：KouZi 文件为空");
            toggle();
        } else if (activeFile != null) {
            ChatUtil.addChatMessage("KouZi 已载入：" + lines.size() + " 行 | " + activeFile.toString());
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        if (++reloadCounter >= 20) {
            reloadCounter = 0;
            if (shouldReload()) {
                loadLines();
                if (lines.isEmpty()) return;
            }
        }
        if (lines.isEmpty()) return;
        int delay = Math.max(1, delayTicks.get());
        if (tickCounter++ < delay) return;
        tickCounter = 0;
        String line = nextLine();
        if (line == null || line.isEmpty()) return;
        if (mc.getNetworkHandler() == null) return;
        String message = buildMessage(line);
        if (message.isEmpty()) return;
        if (whisperMode.get()) {
            String target = whisperTarget.get().trim();
            if (!target.isEmpty()) {
                String clean = message.startsWith("/") ? message.substring(1) : message;
                mc.getNetworkHandler().sendChatCommand("w " + target + " " + clean);
                return;
            }
        }
        if (message.startsWith("/")) {
            mc.getNetworkHandler().sendChatCommand(message.substring(1));
        } else {
            mc.getNetworkHandler().sendChatMessage(message);
        }
    }

    private void loadLines() {
        lineIndex = 0;
        lines = new ArrayList<>();
        try {
            Files.createDirectories(ConfigManager.CONFIG_DIR);
            Path source = resolveSourceFile();
            File file = source.toFile();
            if (!file.exists()) {
                Path parent = source.getParent();
                if (parent != null) {
                    Files.createDirectories(parent);
                }
                source.toFile().createNewFile();
                activeFile = source;
                lastModified = Files.getLastModifiedTime(source).toMillis();
                ChatUtil.addChatMessage("错误：未找到 KouZi 文件，已自动创建");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        lines.add(line);
                    }
                }
            }
            activeFile = source;
            lastModified = Files.getLastModifiedTime(source).toMillis();
        } catch (Exception e) {
            ChatUtil.addChatMessage("错误：读取 KouZi 文件失败");
            lines = List.of();
        }
    }

    private Path resolveSourceFile() {
        if (fileMode.get() == FileMode.Antijijiji) {
            if (Files.exists(PRIMARY_FILE_JIJIJI)) return PRIMARY_FILE_JIJIJI;
            if (Files.exists(ALT_FILE_JIJIJI)) return ALT_FILE_JIJIJI;
            return PRIMARY_FILE_JIJIJI;
        }
        if (fileMode.get() == FileMode.AntiHYW) {
            if (Files.exists(PRIMARY_FILE_HYW)) return PRIMARY_FILE_HYW;
            if (Files.exists(ALT_FILE_HYW)) return ALT_FILE_HYW;
            return PRIMARY_FILE_HYW;
        }
        if (fileMode.get() == FileMode.AntiFuckYou) {
            if (Files.exists(PRIMARY_FILE_TXT)) return PRIMARY_FILE_TXT;
            if (Files.exists(ALT_FILE_TXT)) return ALT_FILE_TXT;
            return PRIMARY_FILE_TXT;
        }
        if (fileMode.get() == FileMode.FuckYou) {
            if (Files.exists(PRIMARY_FILE)) return PRIMARY_FILE;
            if (Files.exists(ALT_FILE)) return ALT_FILE;
            return PRIMARY_FILE;
        }
        if (Files.exists(PRIMARY_FILE)) return PRIMARY_FILE;
        if (Files.exists(PRIMARY_FILE_TXT)) return PRIMARY_FILE_TXT;
        if (Files.exists(ALT_FILE)) return ALT_FILE;
        if (Files.exists(ALT_FILE_TXT)) return ALT_FILE_TXT;
        return PRIMARY_FILE;
    }

    private boolean shouldReload() {
        try {
            Path source = resolveSourceFile();
            if (activeFile == null || !activeFile.equals(source)) return true;
            if (!Files.exists(source)) return true;
            long modified = Files.getLastModifiedTime(source).toMillis();
            return modified != lastModified;
        } catch (Exception e) {
            return true;
        }
    }

    private String nextLine() {
        if (lines.isEmpty()) return null;
        if (lineIndex >= lines.size()) {
            lineIndex = 0;
        }
        return lines.get(lineIndex++);
    }

    private String buildMessage(String base) {
        if (!randomSuffix.get()) return base;
        int len = Math.max(1, suffixLength.get());
        StringBuilder sb = new StringBuilder(base.length() + 1 + len);
        sb.append(base);
        sb.append(' ');
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < len; i++) {
            sb.append(randomSuffixChar(random));
        }
        return sb.toString();
    }

    private char randomSuffixChar(ThreadLocalRandom random) {
        SuffixMode mode = suffixMode.get();
        if (mode == SuffixMode.Smart) {
            int pick = random.nextInt(3);
            mode = pick == 0 ? SuffixMode.Letters : (pick == 1 ? SuffixMode.Numbers : SuffixMode.Traditional);
        }
        return switch (mode) {
            case Letters -> (char) ('a' + random.nextInt(26));
            case Numbers -> (char) ('0' + random.nextInt(10));
            case Traditional -> (char) random.nextInt(0x4E00, 0x9FFF + 1);
            case Smart -> (char) ('a' + random.nextInt(26));
        };
    }
}
