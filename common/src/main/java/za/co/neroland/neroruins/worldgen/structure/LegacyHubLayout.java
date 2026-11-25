package za.co.neroland.neroruins.worldgen.structure;

//? if <1.20.3 {
/*import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

// The vanilla JSON codec caps size at seven on these versions.
record LegacyHubLayout(Structure.StructureSettings settings, Holder<StructureTemplatePool> pool,
        Optional<Identifier> startJigsaw, int depth, HeightProvider startHeight, boolean expansionHack,
        Optional<Heightmap.Types> heightmap, int radius) {
    static final Codec<LegacyHubLayout> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Structure.StructureSettings.CODEC.forGetter(LegacyHubLayout::settings),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(LegacyHubLayout::pool),
            Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(LegacyHubLayout::startJigsaw),
            Codec.intRange(0, 20).fieldOf("size").forGetter(LegacyHubLayout::depth),
            HeightProvider.CODEC.fieldOf("start_height").forGetter(LegacyHubLayout::startHeight),
            Codec.BOOL.fieldOf("use_expansion_hack").forGetter(LegacyHubLayout::expansionHack),
            Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(LegacyHubLayout::heightmap),
            Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(LegacyHubLayout::radius)
    ).apply(instance, LegacyHubLayout::new));

    Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        var chunk = context.chunkPos();
        int y = startHeight.sample(context.random(),
                new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        var start = new BlockPos(chunk.getMinBlockX(), y, chunk.getMinBlockZ());
        return JigsawPlacement.addPieces(context, pool, startJigsaw, depth, start, expansionHack, heightmap, radius);
    }
}
*///?}
