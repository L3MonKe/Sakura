package dev.mzc.client.module.impl.misc;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import dev.mzc.client.values.impl.StringValue;
import meteordevelopment.orbit.EventHandler;

import java.util.concurrent.ThreadLocalRandom;

public class Spam extends Module {
    private enum SuffixMode {
        Letters("字母"),
        Numbers("数字"),
        Traditional("繁体"),
        Random("随机");

        private final String cnName;

        SuffixMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final NumberValue<Integer> delayTicks = new NumberValue<>("Delay", "延迟tick", 20, 1, 100, 1);
    private final StringValue message = new StringValue("Message", "消息", "MZC-Client NB");
    private final BoolValue randomSuffix = new BoolValue("RandomSuffix", "随机后缀", false);
    private final EnumValue<SuffixMode> suffixMode = new EnumValue<>("SuffixMode", "后缀模式", SuffixMode.Letters, SuffixMode.class, randomSuffix::get);
    private final NumberValue<Integer> suffixLength = new NumberValue<>("SuffixLength", "后缀长度", 6, 1, 20, 1, randomSuffix::get);
    private int tickCounter;

    public Spam() {
        super("Spam", "刷屏", Category.Misc);
        this.setType(ModuleType.Safe);
    }

    @Override
    protected void onEnable() {
        tickCounter = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        String msg = buildMessage();
        if (msg == null || msg.isEmpty()) return;
        int delay = Math.max(1, delayTicks.get());
        if (tickCounter++ < delay) return;
        tickCounter = 0;
        if (mc.getNetworkHandler() == null) return;
        if (msg.startsWith("/")) {
            mc.getNetworkHandler().sendChatCommand(msg.substring(1));
        } else {
            mc.getNetworkHandler().sendChatMessage(msg);
        }
    }

    private String buildMessage() {
        String msg = message.get();
        if (msg == null) return null;
        if (!randomSuffix.get()) return msg;
        int len = Math.max(1, suffixLength.get());
        StringBuilder sb = new StringBuilder(msg.length() + 1 + len);
        sb.append(msg);
        sb.append(' ');
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < len; i++) {
            sb.append(randomSuffixChar(random));
        }
        return sb.toString();
    }

    private char randomSuffixChar(ThreadLocalRandom random) {
        SuffixMode mode = suffixMode.get();
        if (mode == SuffixMode.Random) {
            int pick = random.nextInt(3);
            mode = pick == 0 ? SuffixMode.Letters : (pick == 1 ? SuffixMode.Numbers : SuffixMode.Traditional);
        }
        return switch (mode) {
            case Letters -> (char) ('a' + random.nextInt(26));
            case Numbers -> (char) ('0' + random.nextInt(10));
            case Traditional -> (char) random.nextInt(0x4E00, 0x9FFF + 1);
            case Random -> (char) ('a' + random.nextInt(26));
        };
    }
}
