package dev.sakura.client.utils.network.blockage;

import dev.sakura.client.utils.network.blockage.block.NetworkBlock;
import dev.sakura.client.utils.network.blockage.block.PacketTransformer;
import dev.sakura.client.utils.network.blockage.block.PacketValidator;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static dev.sakura.client.Sakura.mc;

public abstract class DirectionalNetworkBlockage<T extends PacketListener> {

    private final List<NetworkBlock> blockageList = new ArrayList<>();
    private final List<BlockedPacket> packetList = new ArrayList<>();
    private long id;
    protected final Object lock = new Object();

    public NetworkBlock newBlockage() {
        return newBlockage(null, null);
    }

    public NetworkBlock newBlockage(PacketTransformer packetTransformer, PacketValidator packetValidator) {
        return newBlockage(packetTransformer, packetValidator, false);
    }

    public NetworkBlock newBlockage(PacketTransformer packetTransformer, PacketValidator packetValidator, boolean priority) {
        synchronized (this.lock) {
            NetworkBlock blockage = new NetworkBlock(packetTransformer, packetValidator, priority, this.getBlockageId());
            this.blockageList.add(blockage);
            return blockage;
        }
    }

    private long getBlockageId() {
        long id = this.id;
        for (NetworkBlock block : this.blockageList) {
            if (block.isPriority() && id >= block.getId()) {
                id = block.getId();
            }
        }
        return id;
    }

    public void releaseBlockage(NetworkBlock networkBlock) {
        synchronized (this.lock) {
            if (this.blockageList.contains(networkBlock)) {
                this.blockageList.remove(networkBlock);
                this.sort();
                this.flush(this.blockageList.isEmpty() ? null : this.blockageList.getFirst().getId(), networkBlock.getPacketTransformer());
            }
        }
    }

    private void flush(@Nullable Long id, @Nullable PacketTransformer packetTransformer) {
        ClientPlayNetworkHandler networkHandler = mc.getNetworkHandler();
        ClientConnection connection;
        if (networkHandler == null) {
            connection = null;
        } else {
            connection = networkHandler.getConnection();
        }
        List<Packet<?>> packetsToFlush = new ArrayList<>();
        for (Iterator<BlockedPacket> iterator = this.packetList.iterator(); iterator.hasNext(); ) {
            BlockedPacket blockedPacket = iterator.next();
            if (id == null || blockedPacket.getId() < id) {
                if (connection != null) {
                    Packet<?> packet = blockedPacket.getPacket();
                    if (packetTransformer != null) {
                        packet = packetTransformer.transform(packet);
                    }
                    if (packet != null) {
                        packetsToFlush.add(packet);
                    }
                }
                iterator.remove();
            }
        }
        for (Packet<?> packet : packetsToFlush) {
            this.flushPacket(connection, packet);
        }
    }

    protected abstract void flushPacket(ClientConnection connection, Packet<?> packet);

    public boolean isBlocked(Packet<?> packet) {
        synchronized (this.lock) {
            if (!this.blockageList.isEmpty()) {
                this.sort();
                final NetworkBlock blockage = this.blockageList.getFirst();
                final PacketValidator packetValidator = blockage.getPacketValidator();
                boolean valid = false;
                if (packetValidator == null) {
                    valid = true;
                } else {
                    if (packetValidator.isValid(packet)) {
                        valid = true;
                    } else {
                        for (final NetworkBlock block : this.blockageList) {
                            if (block.equals(blockage)) {
                                continue;
                            }
                            final PacketValidator blockValidator = block.getPacketValidator();
                            if (blockValidator == null || blockValidator.isValid(packet)) {
                                valid = true;
                                break;
                            }
                        }
                    }
                }
                if (valid) {
                    this.packetList.add(new BlockedPacket(packet, this.id));
                    this.id++;
                    return true;
                }
            }
            return false;
        }
    }

    private void sort() {
        synchronized (this.lock) {
            this.blockageList.sort(Comparator.comparingLong(NetworkBlock::getId));
            this.packetList.sort(Comparator.comparingLong(BlockedPacket::getId));
        }
    }

    public void reset() {
        synchronized (this.lock) {
            this.blockageList.clear();
            this.packetList.clear();
            this.id = 0;
        }
    }

    public boolean isAnyBlockages() {
        synchronized (this.lock) {
            return !this.blockageList.isEmpty();
        }
    }
}
