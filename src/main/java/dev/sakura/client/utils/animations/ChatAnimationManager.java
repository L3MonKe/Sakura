package dev.sakura.client.utils.animations;

import dev.sakura.client.utils.animations.impl.EaseOutSine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatAnimationManager {
    private static final ChatAnimationManager INSTANCE = new ChatAnimationManager();

    private final Map<String, EaseOutSine> animations = new HashMap<>();
    private final Map<Integer, MessageAnimationData> messageAnimations = new ConcurrentHashMap<>();
    private long lastUpdateTime = System.currentTimeMillis();

    public static class MessageAnimationData {
        public final long createdTime;
        public final String messageId;
        public boolean markedForRemoval = false;

        public MessageAnimationData(String messageId) {
            this.messageId = messageId;
            this.createdTime = System.currentTimeMillis();
        }
    }

    private ChatAnimationManager() {
    }

    public static ChatAnimationManager getInstance() {
        return INSTANCE;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        lastUpdateTime = currentTime;
        messageAnimations.entrySet().removeIf(entry -> {
            MessageAnimationData data = entry.getValue();
            return data.markedForRemoval && (currentTime - data.createdTime > 1000);
        });
    }

    public double getChatHudAnimation(String key, double target, int duration) {
        animations.putIfAbsent(key, new EaseOutSine(duration, target));
        EaseOutSine animation = animations.get(key);

        if (Math.abs(animation.getEndPoint() - target) > 0.01) {
            animation.setEndPoint(target);
            animation.reset();
        }

        return animation.getOutput();
    }
}