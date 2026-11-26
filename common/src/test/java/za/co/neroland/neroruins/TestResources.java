package za.co.neroland.neroruins;

import java.io.IOException;
import java.io.InputStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

public final class TestResources {
    private TestResources() { }

    public static CompoundTag readCompressed(InputStream input) throws IOException {
        //? if <1.20.3 {
        /*return NbtIo.readCompressed(input);
        *///?} else {
        return NbtIo.readCompressed(input, net.minecraft.nbt.NbtAccounter.unlimitedHeap());
        //?}
    }

    public static String path(String path) {
        //? if <1.21 {
        /*return path.replaceFirst("^(/data/[^/]+/)(advancement|loot_table|recipe|structure)/", "$1$2s/")
                .replaceFirst("^(/data/[^/]+/tags/)(block|entity_type|fluid|item)/", "$1$2s/");
        *///?} else {
        return path;
        //?}
    }
}
