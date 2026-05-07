package dev.sakura.client.module.impl.player;

import java.awt.*;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;

/**
 * NameProtect 映射管理 - 参考 LiquidBounce 实现
 * 使用简单的字符串匹配算法（不依赖 Aho-Corasick）
 */
public class NameProtectMappings {

    private static final String[] RANDOM_NAMES = {
            "我是南方客户端",
            "我是Southside外挂",
            "SouthsideUser",
            "RhythmUser",
            "我在玩节奏外挂",
            "客户端就得玩Southside",
            "客户端就得玩Rhythm",
            "Southside牛逼666",
            "Rhythm牛逼克拉斯",
            "我可是Zen外挂",
            "zEN么强？",
            "杨浩田1337",
            "我喜欢巴结南方",
            "ZEN之忠犬",
            "最强之南方",
            "Rhythm非常牛逼",
            "最强之Zem",
            "确实打不过ZeM",
            "热注入就得玩Zen",
            "男娘就得玩杨浩田",
            "女人就得玩郭明杰",
            "郭光周",
            "郭丰年",
            "郭艺雯",
            "郭勇",
            "感觉不如Southside"
    };

    private String playerName;
    private String playerReplacement;
    private Map<String, String> otherPlayerMappings = new HashMap<>();
    private List<MappingEntry> allMappings = new ArrayList<>();

    /**
     * 更新映射 - 参考 LiquidBounce 的 update 方法
     */
    public void update(String playerName, String playerReplacement, List<String> otherPlayers) {
        boolean needsUpdate = shouldUpdate(playerName, playerReplacement, otherPlayers);

        if (!needsUpdate) {
            return;
        }

        // 更新玩家名称
        this.playerName = playerName;
        this.playerReplacement = playerReplacement;

        // 为其他玩家生成映射（限制 200 个防止 DoS）
        int limit = Math.min(otherPlayers.size(), 200);
        for (int i = 0; i < limit; i++) {
            String player = otherPlayers.get(i);

            // 验证名称长度（防止 DoS）
            if (player.length() < 3 || player.length() > 20) {
                continue;
            }

            if (!this.otherPlayerMappings.containsKey(player)) {
                String randomName = generateRandomName(player);
                this.otherPlayerMappings.put(player, randomName);
            }
        }

        // 重建映射列表
        rebuildMappings();
    }

    /**
     * 检查是否需要更新 - 参考 LiquidBounce 的 shouldUpdate
     */
    private boolean shouldUpdate(String playerName, String playerReplacement, List<String> otherPlayers) {
        // 玩家名称或替换名称改变
        if (!Objects.equals(this.playerName, playerName) ||
                !Objects.equals(this.playerReplacement, playerReplacement)) {
            return true;
        }

        // 有新的其他玩家
        for (String player : otherPlayers) {
            if (!this.otherPlayerMappings.containsKey(player)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 重建映射列表 - 按名称长度降序排序，避免部分匹配问题
     */
    private void rebuildMappings() {
        allMappings.clear();

        // 添加玩家自己的映射（使用渐变色）
        if (playerName != null && playerReplacement != null) {
            allMappings.add(new MappingEntry(playerName, playerReplacement, true));
        }

        // 添加其他玩家映射（使用白色）
        for (Map.Entry<String, String> entry : otherPlayerMappings.entrySet()) {
            allMappings.add(new MappingEntry(entry.getKey(), entry.getValue(), false));
        }

        // 按原始名称长度降序排序（优先匹配长名称）
        allMappings.sort((a, b) -> Integer.compare(b.originalName.length(), a.originalName.length()));
    }

    /**
     * 在文本中查找需要替换的名称 - 参考 LiquidBounce 的 findReplacements
     * LiquidBounce 返回: List<Pair<Emit, MappingData>>
     * Emit 包含 start 和 end，MappingData 包含 newName 和 colorGetter
     */
    public List<Replacement> findReplacements(CharSequence text) {
        if (text == null || text.length() == 0 || allMappings.isEmpty()) {
            return Collections.emptyList();
        }

        List<Replacement> replacements = new ArrayList<>();
        String textStr = text.toString();

        // 对每个映射进行字符串搜索
        for (MappingEntry mapping : allMappings) {
            int index = 0;
            while ((index = textStr.indexOf(mapping.originalName, index)) != -1) {
                // LiquidBounce: Emit.end 是最后一个字符的索引（不是 length）
                int start = index;
                int end = index + mapping.originalName.length() - 1;

                // 检查是否与已有替换重叠
                boolean overlaps = false;
                for (Replacement existing : replacements) {
                    if (start <= existing.end && end >= existing.start) {
                        overlaps = true;
                        break;
                    }
                }

                if (!overlaps) {
                    replacements.add(new Replacement(
                            start,
                            end,
                            mapping.newName,
                            Color.WHITE,
                            mapping.useGradient
                    ));
                }

                // 移动到下一个可能的位置
                index++;
            }
        }

        // 按起始位置排序（重要！）
        replacements.sort(Comparator.comparingInt(r -> r.start));

        return replacements;
    }

    /**
     * 生成随机玩家名称 - 参考 LiquidBounce 的 getEntropySourceFrom
     * 使用 MD5 哈希确保同一玩家总是得到相同的名称
     */
    private String generateRandomName(String originalName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(originalName.getBytes());
            long seed = ByteBuffer.wrap(hash).getLong();

            int index = (int) Math.abs(seed % RANDOM_NAMES.length);
            return RANDOM_NAMES[index];
        } catch (NoSuchAlgorithmException e) {
            // 降级方案
            int index = Math.abs(originalName.hashCode() % RANDOM_NAMES.length);
            return RANDOM_NAMES[index];
        }
    }

    /**
     * 映射条目
     */
    private static class MappingEntry {
        final String originalName;
        final String newName;
        final boolean useGradient;

        MappingEntry(String originalName, String newName, boolean useGradient) {
            this.originalName = originalName;
            this.newName = newName;
            this.useGradient = useGradient;
        }
    }

    /**
     * 替换信息 - 参考 LiquidBounce 的 MappingData
     */
    public static class Replacement {
        public final int start;
        public final int end;
        public final String newName;
        public final Color color;
        public final boolean useGradient;

        public Replacement(int start, int end, String newName, Color color, boolean useGradient) {
            this.start = start;
            this.end = end;
            this.newName = newName;
            this.color = color;
            this.useGradient = useGradient;
        }
    }
}
