package za.co.neroland.neroruins.guardian;

import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.AABB;
import za.co.neroland.neroruins.NeroRuinsCommon;
import za.co.neroland.neroruins.progression.RuinMilestones;
import za.co.neroland.neroruins.worldgen.structure.ComplexHub;

/**
 * Decides when a Lattice Temple is cleared and credits {@code neroruins:temple_cleared}. A temple is cleared when a
 * Sentinel dies inside a Lattice Temple hub and, in that hub, every Ruin Spawner is spent (dormant) and no other
 * Sentinel is alive. Credit goes to the killer and to every survival or adventure player inside the hub at that
 * moment. Nothing is stored: no damage tally, no names, no positions; the check reads only world state.
 *
 * <p>If part of the hub is not loaded the clearance cannot be confirmed and nobody is credited this time; the
 * next guardian death in a fully loaded temple decides.
 */
public final class TempleClearance {
    static final String TEMPLE_FAMILY = "lattice_temple";

    private TempleClearance() { }

    /** The rule itself, free of the world: no spawner left to wake and no guardian left standing. */
    public static boolean cleared(int activeSpawners, int livingSentinels) {
        return activeSpawners == 0 && livingSentinels == 0;
    }

    /** Called from {@link Sentinel#die} on the server. Never throws into the death path. */
    static void onSentinelDeath(Sentinel sentinel, DamageSource source) {
        if (!(sentinel.level() instanceof ServerLevel level)) return;
        try {
            BoundingBox box = templeAt(level, sentinel.blockPosition());
            if (box == null) return;
            int active = activeSpawners(level, box);
            if (active < 0) return;
            AABB area = AABB.of(box);
            int living = level.getEntitiesOfClass(Sentinel.class, area,
                    other -> other != sentinel && other.isAlive()).size();
            if (!cleared(active, living)) return;
            Set<ServerPlayer> credited = new LinkedHashSet<>();
            if (source != null && source.getEntity() instanceof ServerPlayer killer) credited.add(killer);
            credited.addAll(level.getEntitiesOfClass(ServerPlayer.class, area.inflate(GuardianRules.SPAWNER_RANGE),
                    player -> !player.isSpectator() && !player.isCreative()));
            credited.forEach(RuinMilestones::templeCleared);
        } catch (RuntimeException | LinkageError failure) {
            NeroRuinsCommon.LOGGER.warn("[NeroRuins] Temple clearance check failed ({})", failure.getClass().getName());
        }
    }

    private static BoundingBox templeAt(ServerLevel level, BlockPos pos) {
        var manager = level.structureManager();
        for (Structure structure : manager.getAllStructuresAt(pos).keySet()) {
            if (!(structure instanceof ComplexHub hub) || !TEMPLE_FAMILY.equals(hub.family())) continue;
            StructureStart start = manager.getStructureAt(pos, hub);
            if (start.isValid()) return start.getBoundingBox();
        }
        return null;
    }

    /** Spawners in the hub that are not yet dormant, or {@code -1} if part of the hub is not loaded. */
    private static int activeSpawners(ServerLevel level, BoundingBox box) {
        int active = 0;
        for (int cx = box.minX() >> 4; cx <= box.maxX() >> 4; cx++) {
            for (int cz = box.minZ() >> 4; cz <= box.maxZ() >> 4; cz++) {
                if (!level.hasChunk(cx, cz)) return -1;
                LevelChunk chunk = level.getChunk(cx, cz);
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (entity instanceof RuinSpawnerBlockEntity && box.isInside(entity.getBlockPos())
                            && !entity.getBlockState().getValue(RuinSpawnerBlock.DORMANT)) {
                        active++;
                    }
                }
            }
        }
        return active;
    }
}
