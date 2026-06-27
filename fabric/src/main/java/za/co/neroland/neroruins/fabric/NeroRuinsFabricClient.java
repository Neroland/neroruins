package za.co.neroland.neroruins.fabric;

import net.fabricmc.api.ClientModInitializer;

import za.co.neroland.neroruins.NeroRuinsCommon;

/** Fabric client entry point for NeroRuins. */
public final class NeroRuinsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NeroRuinsCommon.LOGGER.info("[NeroRuins] Fabric client bootstrap");
    }
}
