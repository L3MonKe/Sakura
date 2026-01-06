package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.account.msa.MSAAuthenticator;
import dev.sakura.client.account.type.MinecraftAccount;
import dev.sakura.client.mixin.accessor.IMinecraftClient;
import net.minecraft.client.session.Session;

import java.util.LinkedList;
import java.util.List;

import static dev.sakura.client.Sakura.mc;

public class AccountManager {
    public static final MSAAuthenticator MSA_AUTHENTICATOR = new MSAAuthenticator();
    private final List<MinecraftAccount> accounts = new LinkedList<>();

    public void register(MinecraftAccount account) {
        register(account, true);
    }

    // 注册新账户并选择性地保存到配置
    public void register(MinecraftAccount account, boolean save) {
        for (MinecraftAccount existing : accounts) {
            if (existing.username().equalsIgnoreCase(account.username()) && existing.getClass() == account.getClass()) {
                Sakura.LOGGER.warn("Account already exists: {} ({})", account.username(), account.getClass().getSimpleName());
                return;
            }
        }
        accounts.add(account);
        if (save) Sakura.CONFIG.saveAccounts();
    }

    public void unregister(final MinecraftAccount account) {
        accounts.remove(account);
        Sakura.CONFIG.saveAccounts();
    }

    public void setSession(final Session session) {
        ((IMinecraftClient) mc).setSession(session);
        Sakura.LOGGER.info("Set session to {} ({})", session.getUsername(), session.getUuidOrNull());
    }

    public List<MinecraftAccount> getAccounts() {
        return accounts;
    }

    public boolean isEncrypted() {
        return Sakura.CONFIG.isEncrypted();
    }
}
