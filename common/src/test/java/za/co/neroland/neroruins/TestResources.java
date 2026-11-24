package za.co.neroland.neroruins;

public final class TestResources {
    private TestResources() { }

    public static String path(String path) {
        //? if <1.21 {
        /*return path.replaceFirst("^(/data/[^/]+/)(advancement|loot_table|recipe|structure)/", "$1$2s/")
                .replaceFirst("^(/data/[^/]+/tags/)(block|entity_type|fluid|item)/", "$1$2s/");
        *///?} else {
        return path;
        //?}
    }
}
