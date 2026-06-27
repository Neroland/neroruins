package za.co.neroland.neroruins.fabric;

import net.fabricmc.api.ModInitializer;

import za.co.neroland.neroruins.NeroRuinsCommon;

/** Fabric entry point for NeroRuins. */
public final class NeroRuinsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        NeroRuinsCommon.LOGGER.info("[NeroRuins] Fabric bootstrap");
        NeroRuinsCommon.init();
    }
}
