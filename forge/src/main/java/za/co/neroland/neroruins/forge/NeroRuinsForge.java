package za.co.neroland.neroruins.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import za.co.neroland.neroruins.NeroRuinsCommon;

/** MinecraftForge entry point for NeroRuins. */
@Mod(NeroRuinsCommon.MOD_ID)
public final class NeroRuinsForge {

    public NeroRuinsForge(FMLJavaModLoadingContext context) {
        NeroRuinsCommon.LOGGER.info("[NeroRuins] Forge bootstrap");
        NeroRuinsCommon.init();
    }
}
