package dev.sakura.utils.rotation;

import dev.sakura.utils.vector.Vector2f;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;

import java.util.function.Predicate;

public class RaytraceUtils {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    /**
     * Helper to convert Vector2f rotation (yaw, pitch) to a direction vector.
     */
    public static Vec3d getRotationVector(Vector2f rotation) {
        return getRotationVector(rotation.x, rotation.y);
    }

    public static Vec3d getRotationVector(float yaw, float pitch) {
        float f = yaw * ((float) Math.PI / 180F);
        float g = -pitch * ((float) Math.PI / 180F);
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d((double) (i * k), (double) (-h), (double) (i * j));
    }

    public static BlockHitResult rayTraceCollidingBlocks(Vec3d start, Vec3d end) {
        if (mc.world == null || mc.player == null) return null;
        HitResult result = mc.world.raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.ANY,
                mc.player
        ));

        if (result == null || result.getType() != HitResult.Type.BLOCK) {
            return null;
        }

        return (BlockHitResult) result;
    }

    public static EntityHitResult rayTraceEntity(double range, Vector2f rotation, Predicate<Entity> filter) {
        if (mc.cameraEntity == null) return null;
        Entity entity = mc.cameraEntity;

        Vec3d cameraVec = entity.getEyePos();
        Vec3d rotationVec = getRotationVector(rotation);

        Vec3d endVec = cameraVec.add(rotationVec.x * range, rotationVec.y * range, rotationVec.z * range);
        Box box = entity.getBoundingBox().stretch(rotationVec.multiply(range)).expand(1.0, 1.0, 1.0);

        return ProjectileUtil.raycast(
                entity,
                cameraVec,
                endVec,
                box,
                e -> !e.isSpectator() && e.canHit() && filter.test(e),
                range * range
        );
    }

    public static BlockHitResult rayTraceBlock(double range, Vector2f rotation, BlockPos pos, BlockState state) {
        if (mc.cameraEntity == null || mc.world == null || mc.player == null) return null;
        Entity entity = mc.cameraEntity;

        Vec3d start = entity.getEyePos();
        Vec3d rotationVec = getRotationVector(rotation);

        Vec3d end = start.add(rotationVec.x * range, rotationVec.y * range, rotationVec.z * range);

        return state.getOutlineShape(mc.world, pos, ShapeContext.of(mc.player)).raycast(start, end, pos);
    }

    public static BlockHitResult rayCast(Vector2f rotation, double range) {
        return rayCast(rotation, range, false, 1.0f);
    }

    public static BlockHitResult rayCast(Vector2f rotation, double range, boolean includeFluids, float tickDelta) {
        if (mc.player == null) return null;
        return rayCast(range, includeFluids, mc.player.getCameraPosVec(tickDelta), getRotationVector(rotation), mc.cameraEntity);
    }

    public static BlockHitResult rayCast(double range, boolean includeFluids, Vec3d start, Vec3d direction, Entity entity) {
        if (mc.world == null) return null;
        Vec3d end = start.add(direction.x * range, direction.y * range, direction.z * range);

        return mc.world.raycast(
                new RaycastContext(
                        start,
                        end,
                        RaycastContext.ShapeType.OUTLINE,
                        includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE,
                        entity
                )
        );
    }

    /**
     * Allows you to check if a point is behind a wall
     */
    public static boolean canSeePointFrom(Vec3d eyes, Vec3d vec3) {
        if (mc.world == null || mc.player == null) return false;
        return mc.world.raycast(
                new RaycastContext(
                        eyes, vec3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player
                )
        ).getType() == HitResult.Type.MISS;
    }

    /**
     * Allows you to check if your enemy is behind a wall
     */
    public static boolean facingEnemy(Entity toEntity, double range, Vector2f rotation) {
        return rayTraceEntity(range, rotation, entity -> entity == toEntity) != null;
    }

    public static boolean facingEnemy(Entity fromEntity, Entity toEntity, Vector2f rotation, double range, double wallsRange) {
        Vec3d cameraVec = fromEntity.getEyePos();
        Vec3d rotationVec = getRotationVector(rotation);

        double rangeSquared = range * range;
        double wallsRangeSquared = wallsRange * wallsRange;

        Vec3d endVec = cameraVec.add(rotationVec.x * range, rotationVec.y * range, rotationVec.z * range);
        Box box = fromEntity.getBoundingBox().stretch(rotationVec.multiply(range)).expand(1.0, 1.0, 1.0);

        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                fromEntity, cameraVec, endVec, box, e -> !e.isSpectator() && e.canHit() && e == toEntity, rangeSquared
        );

        if (entityHitResult == null) return false;

        double distance = cameraVec.squaredDistanceTo(entityHitResult.getPos());

        return distance <= rangeSquared && canSeePointFrom(cameraVec, entityHitResult.getPos()) || distance <= wallsRangeSquared;
    }

    /**
     * Allows you to check if a point is behind a wall
     */
    public static boolean facingBlock(Vec3d eyes, Vec3d vec3, BlockPos blockPos, Direction expectedSide, Double expectedMaxRange) {
        if (mc.world == null || mc.player == null) return false;

        BlockHitResult searchedPos = mc.world.raycast(
                new RaycastContext(
                        eyes, vec3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player
                )
        );

        if (searchedPos == null || searchedPos.getType() != HitResult.Type.BLOCK || (expectedSide != null && searchedPos.getSide() != expectedSide)) {
            return false;
        }

        if (expectedMaxRange != null && searchedPos.getPos().squaredDistanceTo(eyes) > expectedMaxRange * expectedMaxRange) {
            return false;
        }

        return searchedPos.getBlockPos().equals(blockPos);
    }
}
