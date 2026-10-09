package za.co.neroland.neroruins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import za.co.neroland.neroruins.config.NeroRuinsConfig;
import za.co.neroland.neroruins.platform.Services;
import za.co.neroland.neroruins.telemetry.NeroRuinsTelemetry;

/**
 * Loader-agnostic entry point for NeroRuins. Each loader entry point
 * (Fabric / Forge / NeoForge) calls {@link #init()} once during mod
 * construction. This is a barebones skeleton — no content is registered yet;
 * add shared blocks, items and systems here and reach loader-specific
 * behaviour through a platform seam.
 */
public final class NeroRuinsCommon {

    public static final String MOD_ID = "neroruins";
    public static final Logger LOGGER = LoggerFactory.getLogger("NeroRuins");

    private NeroRuinsCommon() {
    }

    /** Called once per loader during mod construction. */
    public static void init() {
        LOGGER.info("[NeroRuins] common init");

        // 0. Platform seam, resolved here during construction and never lazily on a tick path — a
        //    late ServiceLoader read can throw ServiceConfigurationError out of gameplay code.
        Services.init();

        // 1. Config first: everything below reads it, including telemetry's opt-out flag.
        NeroRuinsConfig.init();

        // 2. Anonymous, NeroRuins-only crash reporting. Must follow the config registration and
        //    precede the rest of init so early failures are still reported. On by default (opt-out via
        //    telemetryEnabled=false); stays inert while the build's DSN is the placeholder
        //    (see NeroRuinsTelemetry's PLACEHOLDER_DSN guard).
        NeroRuinsTelemetry.init();
    }
}
