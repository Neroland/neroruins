package za.co.neroland.neroruins.guardian;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TempleClearanceTest {
    @Test
    void aTempleIsClearedOnlyWhenNoSpawnerOrGuardianRemains() {
        assertTrue(TempleClearance.cleared(0, 0));
        assertFalse(TempleClearance.cleared(1, 0), "a spawner can still wake");
        assertFalse(TempleClearance.cleared(0, 1), "a guardian is still standing");
    }
}
