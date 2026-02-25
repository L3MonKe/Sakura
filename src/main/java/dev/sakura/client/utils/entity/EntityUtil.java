package dev.sakura.client.utils.entity;

import dev.sakura.client.mixin.accessor.IEntityTrackingSection;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.entity.EntityLookup;
import net.minecraft.world.entity.EntityTrackingSection;
import net.minecraft.world.entity.SectionedEntityCache;
import net.minecraft.world.entity.SimpleEntityLookup;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import static dev.sakura.client.Sakura.mc;

public class EntityUtil {
    public static PlayerEntity getClosestPlayer(double range) {
        return mc.world.getPlayers().stream().filter(e -> !(e instanceof ClientPlayerEntity) && !e.isSpectator())
                .filter(e -> mc.player.squaredDistanceTo(e) <= range * range)
                //todo:.filter(e -> !Managers.SOCIAL.isFriend(e.getName().getString()))
                .min(Comparator.comparingDouble(e -> mc.player.squaredDistanceTo(e))).orElse(null);
    }

    public static boolean isInsideBlock() {
        if (mc.world.getBlockState(BlockPos.ofFloored(mc.player.getPos())).getBlock() == Blocks.ENDER_CHEST)
            return true;
        return mc.world.canCollide(mc.player, mc.player.getBoundingBox());
    }

    public static boolean intersectsWithEntity(Box box, Predicate<Entity> predicate) {
        EntityLookup<Entity> entityLookup = mc.world.getEntityLookup();

        if (entityLookup instanceof SimpleEntityLookup<Entity> simpleEntityLookup) {
            SectionedEntityCache<Entity> cache = simpleEntityLookup.cache;
            LongSortedSet trackedPositions = cache.trackedPositions;
            Long2ObjectMap<EntityTrackingSection<Entity>> trackingSections = cache.trackingSections;

            int i = ChunkSectionPos.getSectionCoord(box.minX - 2);
            int j = ChunkSectionPos.getSectionCoord(box.minY - 2);
            int k = ChunkSectionPos.getSectionCoord(box.minZ - 2);
            int l = ChunkSectionPos.getSectionCoord(box.maxX + 2);
            int m = ChunkSectionPos.getSectionCoord(box.maxY + 2);
            int n = ChunkSectionPos.getSectionCoord(box.maxZ + 2);

            for (int o = i; o <= l; o++) {
                long p = ChunkSectionPos.asLong(o, 0, 0);
                long q = ChunkSectionPos.asLong(o, -1, -1);
                LongBidirectionalIterator longIterator = trackedPositions.subSet(p, q + 1).iterator();

                while (longIterator.hasNext()) {
                    long r = longIterator.nextLong();
                    int s = ChunkSectionPos.unpackY(r);
                    int t = ChunkSectionPos.unpackZ(r);

                    if (s >= j && s <= m && t >= k && t <= n) {
                        EntityTrackingSection<Entity> entityTrackingSection = trackingSections.get(r);

                        if (entityTrackingSection != null && entityTrackingSection.getStatus().shouldTrack()) {
                            for (Entity entity : ((IEntityTrackingSection) entityTrackingSection).<Entity>getCollection()) {
                                if (entity.getBoundingBox().intersects(box) && predicate.test(entity)) return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        AtomicBoolean found = new AtomicBoolean(false);

        entityLookup.forEachIntersects(box, entity -> {
            if (!found.get() && predicate.test(entity)) found.set(true);
        });

        return found.get();
    }

    public static boolean isInWeb(PlayerEntity player) {
        if (mc.world == null || player == null) return false;
        BlockPos pos = player.getBlockPos();
        return mc.world.getBlockState(pos).getBlock() == Blocks.COBWEB ||
                mc.world.getBlockState(pos.up()).getBlock() == Blocks.COBWEB;
    }
}