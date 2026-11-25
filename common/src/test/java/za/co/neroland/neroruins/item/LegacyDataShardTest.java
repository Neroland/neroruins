package za.co.neroland.neroruins.item;

//? if <1.20.5 {
/*import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import za.co.neroland.neroruins.lore.LogCatalog;

class LegacyDataShardTest {
    @BeforeAll
    static void bootstrapMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void knownLogIdSurvivesStackSerialization() {
        ItemStack shard = new ItemStack(Items.PAPER);
        String id = LogCatalog.ids().get(0);
        DataShardItem.setLogId(shard, id);

        ItemStack restored = ItemStack.of(shard.save(new net.minecraft.nbt.CompoundTag()));
        assertEquals(id, DataShardItem.logId(restored));
        assertTrue(LogCatalog.translationPrefix(DataShardItem.logId(restored)).isPresent());
    }

    @Test
    void malformedLogIdCannotUnlockALog() {
        ItemStack shard = new ItemStack(Items.PAPER);
        assertTrue(LogCatalog.translationPrefix(DataShardItem.logId(shard)).isEmpty());
        DataShardItem.setLogId(shard, "neroruins:missing");
        assertTrue(LogCatalog.translationPrefix(DataShardItem.logId(shard)).isEmpty());
    }
}
*///?}
