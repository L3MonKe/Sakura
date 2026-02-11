package dev.sakura.client.utils.path;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.*;

public final class AStarPathfinder {
    private static final int MAX_ITERATIONS = 500;
    private static final double STOP_RANGE_SQ = 4.0;

    private static final List<BlockPos> DIRECT_DIRECTIONS = buildDirectDirections();
    private static final BlockPos[] DIAGONAL_DIRECTIONS = new BlockPos[]{
            new BlockPos(-1, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(-1, 0, 1),
            new BlockPos(1, 0, 1)
    };

    private AStarPathfinder() {
    }

    public static List<BlockPos> findPath(World world, Entity collisionEntity, BlockPos start, BlockPos end, int maxCost, boolean allowDiagonal) {
        if (start == null || end == null || world == null || collisionEntity == null) {
            return List.of();
        }

        if (start.getSquaredDistance(end) <= STOP_RANGE_SQ) {
            return List.of();
        }

        PriorityQueue<Node> openQueue = new PriorityQueue<>(Comparator.<Node>comparingInt(n -> n.f).thenComparingInt(n -> n.pos.hashCode()));
        Map<BlockPos, Node> bestNodes = new HashMap<>();
        Set<BlockPos> closed = new HashSet<>();

        Node startNode = new Node(start, null, 0, heuristic(start, end));
        openQueue.add(startNode);
        bestNodes.put(start, startNode);

        int iterations = 0;
        while (!openQueue.isEmpty() && iterations++ < MAX_ITERATIONS) {
            Node current = openQueue.poll();
            if (current == null) {
                break;
            }

            if (closed.contains(current.pos)) {
                continue;
            }
            closed.add(current.pos);

            if (current.pos.getSquaredDistance(end) <= STOP_RANGE_SQ) {
                return buildPath(current);
            }

            for (BlockPos nextPos : getAdjacentPositions(current.pos, allowDiagonal)) {
                if (closed.contains(nextPos)) {
                    continue;
                }
                if (allowDiagonal && isDiagonalMove(current.pos, nextPos)) {
                    BlockPos xStep = new BlockPos(nextPos.getX(), current.pos.getY(), current.pos.getZ());
                    BlockPos zStep = new BlockPos(current.pos.getX(), current.pos.getY(), nextPos.getZ());
                    if (!isPassable(world, collisionEntity, xStep) || !isPassable(world, collisionEntity, zStep)) {
                        continue;
                    }
                }
                if (!isPassable(world, collisionEntity, nextPos)) {
                    continue;
                }

                int tentativeG = current.g + cost(current.pos, nextPos);
                if (tentativeG > maxCost) {
                    continue;
                }

                Node existing = bestNodes.get(nextPos);
                if (existing == null || tentativeG < existing.g) {
                    Node next = new Node(nextPos, current, tentativeG, heuristic(nextPos, end));
                    bestNodes.put(nextPos, next);
                    openQueue.add(next);
                }
            }
        }

        return List.of();
    }

    private static boolean isDiagonalMove(BlockPos from, BlockPos to) {
        int dx = Math.abs(to.getX() - from.getX());
        int dz = Math.abs(to.getZ() - from.getZ());
        return dx == 1 && dz == 1;
    }

    private static List<BlockPos> buildDirectDirections() {
        List<BlockPos> dirs = new ArrayList<>(22);
        dirs.add(new BlockPos(-1, 0, 0));
        dirs.add(new BlockPos(1, 0, 0));
        for (int dy = -9; dy <= -1; dy++) {
            dirs.add(new BlockPos(0, dy, 0));
        }
        for (int dy = 1; dy <= 9; dy++) {
            dirs.add(new BlockPos(0, dy, 0));
        }
        dirs.add(new BlockPos(0, 0, -1));
        dirs.add(new BlockPos(0, 0, 1));
        return Collections.unmodifiableList(dirs);
    }

    private static List<BlockPos> getAdjacentPositions(BlockPos pos, boolean allowDiagonal) {
        List<BlockPos> list = new ArrayList<>(allowDiagonal ? DIRECT_DIRECTIONS.size() + DIAGONAL_DIRECTIONS.length : DIRECT_DIRECTIONS.size());
        for (BlockPos dir : DIRECT_DIRECTIONS) {
            list.add(pos.add(dir));
        }
        if (allowDiagonal) {
            for (BlockPos dir : DIAGONAL_DIRECTIONS) {
                BlockPos diagonal = pos.add(dir);
                list.add(diagonal);
            }
        }
        return list;
    }

    private static boolean isPassable(World world, Entity collisionEntity, BlockPos pos) {
        Box box = new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 2.0, pos.getZ() + 1.0);
        return !world.getBlockCollisions(collisionEntity, box).iterator().hasNext();
    }

    private static int cost(BlockPos from, BlockPos to) {
        int dx = from.getX() - to.getX();
        int dy = from.getY() - to.getY();
        int dz = from.getZ() - to.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    private static int heuristic(BlockPos from, BlockPos to) {
        return cost(from, to);
    }

    private static List<BlockPos> buildPath(Node endNode) {
        ArrayList<BlockPos> path = new ArrayList<>();
        Node current = endNode;
        while (current != null && current.parent != null) {
            path.add(current.pos);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static final class Node {
        private final BlockPos pos;
        private final Node parent;
        private final int g;
        private final int f;

        private Node(BlockPos pos, Node parent, int g, int h) {
            this.pos = pos.toImmutable();
            this.parent = parent;
            this.g = g;
            this.f = g + h;
        }
    }
}
