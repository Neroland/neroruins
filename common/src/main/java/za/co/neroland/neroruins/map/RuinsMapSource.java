package za.co.neroland.neroruins.map;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import net.minecraft.server.MinecraftServer;
import za.co.neroland.nerolandcore.map.MapViewer;
import za.co.neroland.neroruins.config.NeroRuinsConfig;
import za.co.neroland.neroruins.data.ClaimLedger;
import za.co.neroland.neroruins.data.ExpeditionRecord;
import za.co.neroland.neroruins.expedition.RelicPassives;

/**
 * Where the provider reads one viewer's own records. It is only ever asked about the viewer's own UUID and has
 * no way to list another player's data. The server implementation reads the saved data already in memory.
 */
interface RuinsMapSource {

    /** The viewer's own discoveries. */
    List<ExpeditionRecord.Discovery> discoveries(MapViewer viewer);

    /** Locations of vaults the viewer has claimed. */
    Collection<ClaimLedger.Location> claims(MapViewer viewer);

    /**
     * The viewer's live Surveyor's Compass reading from their current position: online viewers holding the
     * compass only, otherwise empty. Point and band only, never the target's position.
     */
    Optional<RelicPassives.CompassReading> compass(MapViewer viewer);

    RuinsMapSource SERVER = new RuinsMapSource() {
        @Override
        public Optional<RelicPassives.CompassReading> compass(MapViewer viewer) {
            if (!viewer.online() || viewer.position().isEmpty()) return Optional.empty();
            var pos = viewer.position().get();
            return RelicPassives.compassReading(viewer.id(), pos.getX(), pos.getZ());
        }

        @Override
        public List<ExpeditionRecord.Discovery> discoveries(MapViewer viewer) {
            MinecraftServer server = viewer.server();
            // A server that has turned position recording off shows no positions at all, even before the
            // hourly sweep has dropped the ones already stored.
            if (server == null || !NeroRuinsConfig.RECORD_POSITIONS.get()) return List.of();
            return ExpeditionRecord.get(server).export(viewer.id());
        }

        @Override
        public Collection<ClaimLedger.Location> claims(MapViewer viewer) {
            MinecraftServer server = viewer.server();
            if (server == null) return List.of();
            return ClaimLedger.get(server).export(viewer.id()).keySet();
        }
    };
}
