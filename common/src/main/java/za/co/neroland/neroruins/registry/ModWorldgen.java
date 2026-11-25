package za.co.neroland.neroruins.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import za.co.neroland.nerolandcore.registry.RegistrationProvider;
import za.co.neroland.neroruins.NeroRuinsCommon;
import za.co.neroland.neroruins.platform.Services;
import za.co.neroland.neroruins.worldgen.placement.SatellitePlacement;
import za.co.neroland.neroruins.worldgen.structure.ComplexHub;
import za.co.neroland.neroruins.worldgen.structure.SatelliteStructure;

//? if >=26.3 {
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
//?} else {
/*import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
*///?}

public final class ModWorldgen {
    private static final RegistrationProvider<StructureType<?>> STRUCTURES =
            Services.REGISTRATION.provider(Registries.STRUCTURE_TYPE, NeroRuinsCommon.MOD_ID);

    public static final RegistrationProvider.RegistryEntry<StructureType<ComplexHub>> COMPLEX_HUB =
            //? if >=1.20.5 {
            STRUCTURES.register("complex_hub", key -> () -> ComplexHub.CODEC);
            //?} else {
            /*STRUCTURES.register("complex_hub", key -> () -> ComplexHub.CODEC.codec());
            *///?}
    public static final RegistrationProvider.RegistryEntry<StructureType<SatelliteStructure>> SATELLITE_STRUCTURE =
            //? if >=1.20.5 {
            STRUCTURES.register("satellite", key -> () -> SatelliteStructure.CODEC);
            //?} else {
            /*STRUCTURES.register("satellite", key -> () -> SatelliteStructure.CODEC.codec());
            *///?}

    //? if >=26.3 {
    private static final RegistrationProvider<MapCodec<? extends StructurePlacement>> PLACEMENTS =
            Services.REGISTRATION.provider(Registries.STRUCTURE_PLACEMENT, NeroRuinsCommon.MOD_ID);
    public static final RegistrationProvider.RegistryEntry<MapCodec<SatellitePlacement>> SATELLITE_PLACEMENT =
            PLACEMENTS.register("satellite", key -> SatellitePlacement.CODEC);
    //?} else {
    /*private static final RegistrationProvider<StructurePlacementType<?>> PLACEMENTS =
            Services.REGISTRATION.provider(Registries.STRUCTURE_PLACEMENT, NeroRuinsCommon.MOD_ID);
    public static final RegistrationProvider.RegistryEntry<StructurePlacementType<SatellitePlacement>> SATELLITE_PLACEMENT =
            //? if >=1.20.5 {
            PLACEMENTS.register("satellite", key -> () -> SatellitePlacement.CODEC);
            //?} else {
            PLACEMENTS.register("satellite", key -> () -> SatellitePlacement.CODEC.codec());
            //?}
    *///?}

    private ModWorldgen() { }

    public static void init() {
        ModProcessors.init();
    }
}
