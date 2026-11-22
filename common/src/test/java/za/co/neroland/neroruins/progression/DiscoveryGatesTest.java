package za.co.neroland.neroruins.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

/** Every family gate the tracker opens exists as a player-scope record gate, and unknown families open nothing. */
class DiscoveryGatesTest {
    @Test
    void everyFamilyHasARecordGate() throws Exception {
        assertEquals(6, RuinMilestones.FAMILIES.size());
        for (String family : RuinMilestones.FAMILIES) {
            assertEquals("neroruins:discovered/" + family,
                    RuinMilestones.discoveredGate(family).orElseThrow().toString());
            String path = "/data/neroruins/neroland_gates/discovered/" + family + ".json";
            try (InputStream in = DiscoveryGatesTest.class.getResourceAsStream(path)) {
                assertNotNull(in, "missing gate file " + path);
                String json = new String(in.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                assertTrue(json.contains("\"scope\": \"player\""), path);
            }
        }
    }

    @Test
    void aFamilyWithoutAGateOpensNothing() {
        assertTrue(RuinMilestones.discoveredGate("custom_pack_family").isEmpty());
    }

    @Test
    void theTempleGateFileExists() {
        assertNotNull(DiscoveryGatesTest.class.getResourceAsStream("/data/neroruins/neroland_gates/temple_cleared.json"));
    }
}
