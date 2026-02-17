package dev.sakura.client.utils.network.blockage.block;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class NetworkBlock {
    private @Nullable PacketTransformer packetTransformer; // transforms packets before sending
    private final @Nullable PacketValidator packetValidator; // validates whether the packet should be blocked
    private final boolean priority;
    private final long id;
    private final long creationTime = System.currentTimeMillis();

    public NetworkBlock(@Nullable PacketTransformer packetTransformer, @Nullable PacketValidator packetValidator, boolean priority, long id) {
        this.packetTransformer = packetTransformer;
        this.packetValidator = packetValidator;
        this.priority = priority;
        this.id = id;
    }

    public @Nullable PacketTransformer getPacketTransformer() {
        return packetTransformer;
    }

    public void setPacketTransformer(@Nullable PacketTransformer packetTransformer) {
        this.packetTransformer = packetTransformer;
    }

    public @Nullable PacketValidator getPacketValidator() {
        return packetValidator;
    }

    public long getId() {
        return id;
    }

    public boolean isPriority() {
        return priority;
    }

    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NetworkBlock block = (NetworkBlock) o;
        return id == block.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
