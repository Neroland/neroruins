package za.co.neroland.neroruins.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

/** Core export (Core 1.20.1): the shared document holds the player's own records and no UUID. */
class ArchiveDocumentTest {

    @Test
    void documentCarriesTheArchiveAndNoIdentifier() {
        JsonObject doc = ArchiveExport.document(Map.of("lore:first_signal", Long.valueOf(42L)), Map.of(), Map.of(),
                List.of());
        assertFalse(doc.has("player"), "the Core export carries no UUID");
        assertEquals(42L, doc.getAsJsonObject("archive").get("lore:first_signal").getAsLong());
        assertEquals(0, doc.getAsJsonArray("vault_claims").size());
        assertEquals(0, doc.getAsJsonArray("held_vault_rewards").size());
        assertEquals(0, doc.getAsJsonArray("expeditions").size());
    }
}
