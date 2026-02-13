package dev.sakura.client.utils.rotation;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.function.Predicate;

import static dev.sakura.client.Sakura.mc;

public class RaytraceUtil {
    public static Vec3d getRotationVector(Rotation rotation) {
        return getRotationVector(rotation.yaw, rotation.pitch);
    }

    public static Vec3d getRotationVector(float yaw, float pitch) {
        return Vec3d.fromPolar(pitch, yaw);
    }

    public static BlockHitResult rayTraceCollidingBlocks(Vec3d start, Vec3d end) {
        if (mc.world == null || mc.player == null) return null;
        BlockHitResult result = mc.world.raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.ANY,
                mc.player
        ));

        if (result == null || result.getType() != HitResult.Type.BLOCK) {
            return null;
        }

        return result;
    }

    public static EntityHitResult rayTraceEntity(double range, Rotation rotation, Predicate<Entity> filter) {
        if (mc.getCameraEntity() == null) return null;
        Entity entity = mc.getCameraEntity();

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

    public static BlockHitResult rayTraceBlock(double range, Rotation rotation, BlockPos pos, BlockState state) {
        if (mc.getCameraEntity() == null || mc.world == null || mc.player == null) return null;
        Entity entity = mc.getCameraEntity();

        Vec3d start = entity.getEyePos();
        Vec3d rotationVec = getRotationVector(rotation);

        Vec3d end = start.add(rotationVec.x * range, rotationVec.y * range, rotationVec.z * range);

        return state.getOutlineShape(mc.world, pos, ShapeContext.of(mc.player)).raycast(start, end, pos);
    }

    public static BlockHitResult rayCast(Rotation rotation, double range) {
        return rayCast(rotation, range, false, 1.0f);
    }

    public static BlockHitResult rayCast(Rotation rotation, double range, boolean includeFluids, float tickDelta) {
        if (mc.player == null) return null;
        return rayCast(range, includeFluids, mc.player.getCameraPosVec(tickDelta), getRotationVector(rotation), mc.getCameraEntity());
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

    public static boolean canSeePointFrom(Vec3d eyes, Vec3d vec3) {
        if (mc.world == null || mc.player == null) return false;
        return mc.world.raycast(
                new RaycastContext(
                        eyes, vec3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player
                )
        ).getType() == HitResult.Type.MISS;
    }

    public static boolean facingEnemy(Entity toEntity, double range, Rotation rotation) {
        return rayTraceEntity(range, rotation, entity -> entity == toEntity) != null;
    }

    public static boolean facingEnemy(Entity fromEntity, Entity toEntity, Rotation rotation, double range, double wallsRange) {
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

    public static boolean overBlock(final Rotation rotation, final Direction direction, final BlockPos pos) {
        return overBlock(rotation, direction, pos, false);
    }

    public static Boolean overBlock(final Rotation rotation, final BlockPos pos, boolean strict) {
        return overBlock(rotation, Direction.UP, pos, strict);
    }

    public static boolean overBlock(final Rotation rotation, final Direction direction, final BlockPos pos, final boolean strict) {
        Vec3d cameraPos = mc.player.getCameraPosVec(1.0F);
        Vec3d rotationVec = Vec3d.fromPolar(rotation.pitch, rotation.yaw);
        Vec3d reachVec = cameraPos.add(rotationVec.multiply(4.5));

        BlockHitResult hitResult = mc.world.raycast(new RaycastContext(cameraPos, reachVec, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));

        if (hitResult.getType() == HitResult.Type.MISS) {
            return false;
        }

        boolean samePos = hitResult.getBlockPos().equals(pos);
        boolean sameSide = !strict || hitResult.getSide() == direction;

        return samePos && sameSide;
    }
}
