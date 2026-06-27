package za.co.neroland.neroruins.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import za.co.neroland.neroruins.NeroRuinsCommon;

/** NeoForge entry point for NeroRuins. */
@Mod(NeroRuinsCommon.MOD_ID)
public final class NeroRuinsNeoForge {

    public NeroRuinsNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        NeroRuinsCommon.LOGGER.info("[NeroRuins] NeoForge bootstrap");
        NeroRuinsCommon.init();
    }
}
