package za.co.neroland.neroruins.data;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.gson.JsonElement;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import za.co.neroland.nerolandcore.data.PlayerDataErasure;
import za.co.neroland.nerolandcore.data.PlayerDataExport;
import za.co.neroland.neroruins.NeroRuinsCommon;

public final class RuinPlayerData {
    public interface Store {
        void erase(MinecraftServer server, UUID player);
        void backupNow(MinecraftServer server, UUID player);
    }

    private static final List<Store> STORES = new CopyOnWriteArrayList<>();
    private static final AtomicBoolean REGISTERED = new AtomicBoolean();

    private RuinPlayerData() { }

    public static void init() {
        if (REGISTERED.compareAndSet(false, true)) {
            register(PlayerArchive.erasureStore());
            register(ClaimLedger.erasureStore());
            register(ExpeditionRecord.erasureStore());
            PlayerDataErasure.register(RuinPlayerData::erase);
            PlayerDataExport.register(NeroRuinsCommon.MOD_ID, RuinPlayerData::coreExport);
        }
    }

    public static void register(Store store) {
        STORES.add(store);
    }

    /**
     * Core {@code PlayerDataExporter}: the same document as {@code /neroruins privacy export}, read from the
     * saved stores (so it works for offline players), without the UUID, or {@code null} when NeroRuins holds
     * nothing for the player. Nothing is logged or written to disk.
     */
    @Nullable
    public static JsonElement coreExport(MinecraftServer server, UUID player) {
        var archive = PlayerArchive.get(server).export(player);
        var ledger = ClaimLedger.get(server);
        var claims = ledger.export(player);
        var reservations = ledger.exportReservations(player);
        var expeditions = ExpeditionRecord.get(server).export(player);
        if (archive.isEmpty() && claims.isEmpty() && reservations.isEmpty() && expeditions.isEmpty()) {
            return null;
        }
        return ArchiveExport.document(archive, claims, reservations, expeditions);
    }

    private static void erase(MinecraftServer server, UUID player) {
        eraseStores(STORES, server, player);
    }

    static void eraseStores(List<Store> stores, MinecraftServer server, UUID player) {
        int failures = 0;
        for (Store store : stores) {
            try {
                store.erase(server, player);
                store.backupNow(server, player);
            } catch (Throwable failure) {
                failures++;
                NeroRuinsCommon.LOGGER.warn("[NeroRuins] Player-data erasure or backup failed in {} ({})",
                        store.getClass().getName(), failure.getClass().getName());
            }
        }
        if (failures > 0) {
            throw new IllegalStateException("NeroRuins could not erase or refresh " + failures + " player-data store(s)");
        }
    }
}
