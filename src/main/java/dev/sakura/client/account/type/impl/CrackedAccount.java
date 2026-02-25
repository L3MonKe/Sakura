package dev.sakura.client.account.type.impl;

import dev.sakura.client.account.type.MinecraftAccount;
import net.minecraft.client.session.Session;

import java.util.Optional;
import java.util.UUID;

public record CrackedAccount(String username) implements MinecraftAccount {
    @Override
    public Session login() {
        return new Session(username(),
                UUID.randomUUID(), "", Optional.empty(),
                Optional.empty(), Session.AccountType.LEGACY);
    }
}
