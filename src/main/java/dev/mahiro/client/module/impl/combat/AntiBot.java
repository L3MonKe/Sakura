package dev.mahiro.client.module.impl.combat;

import com.mojang.authlib.GameProfile;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.BitSet;
import java.util.Collection;
import java.util.Objects;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", "防假人", Category.Combat);
    }

    private final BoolValue literalNpc = new BoolValue("Literal NPC", "严格NPC", false);

    private final BoolValue nameCheck = new BoolValue("Name", "名字检测", true);
    private final NumberValue<Integer> minNameLength = new NumberValue<>("Min Name Len", "最小名字长度", 3, 1, 32, 1, nameCheck::get);
    private final NumberValue<Integer> maxNameLength = new NumberValue<>("Max Name Len", "最大名字长度", 16, 1, 32, 1, nameCheck::get);
    private final BoolValue validateNameChars = new BoolValue("Validate Chars", "校验名字字符", true, nameCheck::get);

    private final BoolValue duplicateName = new BoolValue("Duplicate Name", "重复名字", true);
    private final BoolValue illegalPitch = new BoolValue("Illegal Pitch", "异常视角", true);
    private final BoolValue ageCheck = new BoolValue("Age", "存活时间", false);
    private final NumberValue<Integer> minAgeTicks = new NumberValue<>("MinAge", "最小存活Tick", 20, 0, 200, 1, ageCheck::get);

    private static final BitSet VALID_NAME_CHARS = new BitSet(128);

    static {
        for (int c = '0'; c <= '9'; c++) VALID_NAME_CHARS.set(c);
        for (int c = 'a'; c <= 'z'; c++) VALID_NAME_CHARS.set(c);
        for (int c = 'A'; c <= 'Z'; c++) VALID_NAME_CHARS.set(c);
        VALID_NAME_CHARS.set('_');
    }

    public static boolean isBot(Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return false;
        return isBot(player);
    }

    public static boolean isBot(PlayerEntity player) {
        AntiBot antiBot = Mahiro.MODULES == null ? null : Mahiro.MODULES.getModule(AntiBot.class);
        if (antiBot == null || !antiBot.isEnabled()) return false;
        return antiBot.isBotInternal(player);
    }

    private boolean isBotInternal(PlayerEntity player) {
        if (nullCheck() || player == mc.player) return false;

        if (literalNpc.get()) {
            if (mc.getNetworkHandler() == null) return false;
            if (mc.getNetworkHandler().getPlayerListEntry(player.getUuid()) == null) return true;
        }

        if (ageCheck.get() && player.age < minAgeTicks.get()) return true;
        if (illegalPitch.get()) {
            float pitch = player.getPitch();
            if (!Float.isFinite(pitch) || pitch < -90.0f || pitch > 90.0f) return true;
        }

        if (nameCheck.get() && isInvalidName(player)) return true;
        if (duplicateName.get() && isDuplicateName(player.getGameProfile())) return true;

        return false;
    }

    private boolean isInvalidName(PlayerEntity player) {
        GameProfile profile = player.getGameProfile();
        if (profile == null) return true;
        String name = profile.getName();
        if (name == null) return true;

        int min = Math.min(minNameLength.get(), maxNameLength.get());
        int max = Math.max(minNameLength.get(), maxNameLength.get());
        if (name.length() < min || name.length() > max) return true;

        if (!validateNameChars.get()) return false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c >= 128 || !VALID_NAME_CHARS.get(c)) return true;
        }
        return false;
    }

    private boolean isDuplicateName(GameProfile profile) {
        if (mc.getNetworkHandler() == null) return false;
        Collection<PlayerListEntry> list = mc.getNetworkHandler().getPlayerList();
        if (list == null) return false;
        String name = profile == null ? null : profile.getName();
        if (name == null) return false;

        int duplicates = 0;
        for (PlayerListEntry entry : list) {
            if (entry == null) continue;
            GameProfile p = entry.getProfile();
            if (p == null) continue;
            if (Objects.equals(p.getName(), name) && !Objects.equals(p.getId(), profile.getId())) {
                duplicates++;
                if (duplicates >= 1) return true;
            }
        }
        return false;
    }

}
