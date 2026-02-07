package dev.sakura.client.config;

import dev.sakura.client.utils.client.ChatUtil;
import dev.sakura.client.verify.AuthState;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.client.verify.client.IRCHandler;
import dev.sakura.client.verify.client.IRCTransport;
import net.minecraft.client.MinecraftClient;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public final class CloudConfigService {
    public record ListResult(boolean success, List<String> names, int max, String message) {
    }

    public record GetResult(boolean success, String owner, String name, String content, String message) {
    }

    public record UploadResult(boolean success, String message, int max) {
    }

    public record DeleteResult(boolean success, String owner, String name, String message) {
    }

    private static final long TIMEOUT_MS = 10_000L;

    private final ConcurrentHashMap<String, CompletableFuture<?>> pending = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "cloud-config");
        t.setDaemon(true);
        return t;
    });

    private final IRCHandler handler = new IRCHandler() {
        @Override
        public void onMessage(String sender, String message) {
        }

        @Override
        public void onDisconnected(String message) {
            failAll(new IllegalStateException("Disconnected: " + (message == null ? "" : message)));
        }

        @Override
        public void onConnected() {
        }

        @Override
        public String getInGameUsername() {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return mc.getSession().getUsername();
            return mc.player.getName().getString();
        }

        @Override
        public void onCloudConfigUploadResult(boolean success, String message, int max) {
            @SuppressWarnings("unchecked")
            CompletableFuture<UploadResult> f = (CompletableFuture<UploadResult>) pending.remove("upload");
            if (f != null) {
                f.complete(new UploadResult(success, message, max));
            }
            MinecraftClient.getInstance().execute(() -> {
                if (success) {
                    ChatUtil.addChatMessage("§a云配置上传成功！");
                } else {
                    ChatUtil.addChatMessage("§c云配置上传失败: " + (message == null ? "" : message));
                }
            });
        }

        @Override
        public void onCloudConfigGetResult(boolean success, String owner, String name, String content, String message) {
            String k1 = key("get", owner, name);
            @SuppressWarnings("unchecked")
            CompletableFuture<GetResult> f = (CompletableFuture<GetResult>) pending.remove(k1);
            if (f == null) {
                String current = AuthState.getCurrentUser();
                if (owner != null && !owner.isEmpty() && owner.equals(current)) {
                    @SuppressWarnings("unchecked")
                    CompletableFuture<GetResult> f2 = (CompletableFuture<GetResult>) pending.remove(key("get", "", name));
                    f = f2;
                }
            }
            if (f != null) {
                f.complete(new GetResult(success, owner, name, content, message));
            }
            MinecraftClient.getInstance().execute(() -> {
                if (success) {
                    dev.sakura.client.Sakura.CONFIG.loadConfigFromString(content);
                    if (owner != null && !owner.isEmpty()) {
                        ChatUtil.addChatMessage("§a已加载用户 " + owner + " 的云配置 " + name + "！");
                    } else {
                        ChatUtil.addChatMessage("§a云配置 " + name + " 加载成功！");
                    }
                } else {
                    ChatUtil.addChatMessage("§c云配置加载失败: " + (message == null ? "" : message));
                }
            });
        }

        @Override
        public void onCloudConfigListResult(boolean success, List<String> names, int max, String message) {
            @SuppressWarnings("unchecked")
            CompletableFuture<ListResult> f = (CompletableFuture<ListResult>) pending.remove("list");
            if (f != null) {
                f.complete(new ListResult(success, names == null ? List.of() : List.copyOf(names), max, message));
            }
            MinecraftClient.getInstance().execute(() -> {
                if (success) {
                    List<String> list = names == null ? List.of() : names;
                    ChatUtil.addChatMessage("§e云配置列表 (" + list.size() + "/" + max + "):");
                    for (String n : list) {
                        ChatUtil.addChatMessage("§7 - §f" + n);
                    }
                } else {
                    ChatUtil.addChatMessage("§c获取云配置列表失败: " + (message == null ? "" : message));
                }
            });
        }

        @Override
        public void onCloudConfigDeleteResult(boolean success, String owner, String name, String message) {
            String k1 = key("delete", owner, name);
            @SuppressWarnings("unchecked")
            CompletableFuture<DeleteResult> f = (CompletableFuture<DeleteResult>) pending.remove(k1);
            if (f == null) {
                String current = AuthState.getCurrentUser();
                if (owner != null && !owner.isEmpty() && current != null && owner.equals(current)) {
                    @SuppressWarnings("unchecked")
                    CompletableFuture<DeleteResult> f2 = (CompletableFuture<DeleteResult>) pending.remove(key("delete", "", name));
                    f = f2;
                }
            }
            if (f != null) {
                f.complete(new DeleteResult(success, owner, name, message));
            }
            MinecraftClient.getInstance().execute(() -> {
                if (success) {
                    ChatUtil.addChatMessage("§a云配置 " + name + " 删除成功！");
                } else {
                    ChatUtil.addChatMessage("§c云配置删除失败: " + (message == null ? "" : message));
                }
            });
        }
    };

    public IRCHandler asHandler() {
        return handler;
    }

    public CompletableFuture<ListResult> list() {
        IRCTransport t = requireTransport();
        CompletableFuture<ListResult> f = new CompletableFuture<>();
        putPending("list", f);
        t.listCloudConfigs();
        scheduleTimeout("list", f);
        return f;
    }

    public CompletableFuture<GetResult> get(String owner, String name) {
        IRCTransport t = requireTransport();
        String o = owner == null ? "" : owner.trim();
        String n = Objects.requireNonNull(name, "name").trim();
        CompletableFuture<GetResult> f = new CompletableFuture<>();
        String k = key("get", o, n);
        putPending(k, f);
        if (o.isEmpty()) {
            t.getCloudConfig(n);
        } else {
            t.getCloudConfig(o, n);
        }
        scheduleTimeout(k, f);
        return f;
    }

    public CompletableFuture<UploadResult> upload(String name, String content) {
        IRCTransport t = requireTransport();
        String n = Objects.requireNonNull(name, "name").trim();
        CompletableFuture<UploadResult> f = new CompletableFuture<>();
        putPending("upload", f);
        t.uploadCloudConfig(n, content == null ? "" : content);
        scheduleTimeout("upload", f);
        return f;
    }

    public CompletableFuture<DeleteResult> delete(String owner, String name) {
        IRCTransport t = requireTransport();
        String o = owner == null ? "" : owner.trim();
        String n = Objects.requireNonNull(name, "name").trim();
        CompletableFuture<DeleteResult> f = new CompletableFuture<>();
        String k = key("delete", o, n);
        putPending(k, f);
        if (o.isEmpty()) {
            t.deleteCloudConfig(n);
        } else {
            t.deleteCloudConfig(o, n);
        }
        scheduleTimeout(k, f);
        return f;
    }

    private IRCTransport requireTransport() {
        IRCTransport t = VerificationClient.getTransport();
        if (t == null) {
            throw new IllegalStateException("Transport is null");
        }
        if (!AuthState.isAuthed()) {
            throw new IllegalStateException("Not authed");
        }
        return t;
    }

    private void putPending(String key, CompletableFuture<?> f) {
        CompletableFuture<?> old = pending.put(key, f);
        if (old != null) {
            old.completeExceptionally(new IllegalStateException("Replaced by new request"));
        }
    }

    private void scheduleTimeout(String key, CompletableFuture<?> f) {
        scheduler.schedule(() -> {
            if (f.isDone()) {
                return;
            }
            CompletableFuture<?> removed = pending.remove(key);
            if (removed != null) {
                removed.completeExceptionally(new java.util.concurrent.TimeoutException("Timeout: " + key));
            }
        }, TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }

    private void failAll(Exception e) {
        for (String k : pending.keySet()) {
            CompletableFuture<?> f = pending.remove(k);
            if (f != null) {
                f.completeExceptionally(e);
            }
        }
    }

    private static String key(String action, String owner, String name) {
        return action + "|" + (owner == null ? "" : owner) + "|" + (name == null ? "" : name);
    }
}
