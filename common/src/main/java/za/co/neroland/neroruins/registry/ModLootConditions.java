package za.co.neroland.neroruins.registry;

import net.minecraft.core.registries.Registries;
import za.co.neroland.nerolandcore.registry.RegistrationProvider;
import za.co.neroland.neroruins.NeroRuinsCommon;
import za.co.neroland.neroruins.loot.ModLoadedCondition;
import za.co.neroland.neroruins.loot.RelicChanceCondition;
import za.co.neroland.neroruins.platform.Services;

//? if >=26.1 {
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
//?} else {
/*import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
*///?}

/** Loot item condition types. 26.x registers the condition's MapCodec; 1.21.x wraps it in a type. */
public final class ModLootConditions {
    //? if >=26.1 {
    private static final RegistrationProvider<MapCodec<? extends LootItemCondition>> CONDITIONS =
            Services.REGISTRATION.provider(Registries.LOOT_CONDITION_TYPE, NeroRuinsCommon.MOD_ID);
    public static final RegistrationProvider.RegistryEntry<MapCodec<ModLoadedCondition>> MOD_LOADED =
            CONDITIONS.register("mod_loaded", key -> ModLoadedCondition.CODEC);
    public static final RegistrationProvider.RegistryEntry<MapCodec<RelicChanceCondition>> RELIC_CHANCE =
            CONDITIONS.register("relic_chance", key -> RelicChanceCondition.CODEC);
    //?} else {
    /*private static final RegistrationProvider<LootItemConditionType> CONDITIONS =
            Services.REGISTRATION.provider(Registries.LOOT_CONDITION_TYPE, NeroRuinsCommon.MOD_ID);
    public static final RegistrationProvider.RegistryEntry<LootItemConditionType> MOD_LOADED =
            //? if >=1.20.5 {
            CONDITIONS.register("mod_loaded", key -> new LootItemConditionType(ModLoadedCondition.CODEC));
            //?} else if >=1.20.2 {
            CONDITIONS.register("mod_loaded", key -> new LootItemConditionType(ModLoadedCondition.CODEC.codec()));
            //?} else {
            CONDITIONS.register("mod_loaded", key -> new LootItemConditionType(new LegacyConditionSerializer<>(ModLoadedCondition.CODEC.codec())));
            //?}
    public static final RegistrationProvider.RegistryEntry<LootItemConditionType> RELIC_CHANCE =
            //? if >=1.20.5 {
            CONDITIONS.register("relic_chance", key -> new LootItemConditionType(RelicChanceCondition.CODEC));
            //?} else if >=1.20.2 {
            CONDITIONS.register("relic_chance", key -> new LootItemConditionType(RelicChanceCondition.CODEC.codec()));
            //?} else {
            CONDITIONS.register("relic_chance", key -> new LootItemConditionType(new LegacyConditionSerializer<>(RelicChanceCondition.CODEC.codec())));
            //?}
    *///?}

    //? if <1.20.2 {
    /*private record LegacyConditionSerializer<T extends net.minecraft.world.level.storage.loot.predicates.LootItemCondition>(
            com.mojang.serialization.Codec<T> codec) implements net.minecraft.world.level.storage.loot.Serializer<T> {
        @Override
        public void serialize(com.google.gson.JsonObject json, T condition, com.google.gson.JsonSerializationContext context) {
            var encoded = codec.encodeStart(com.mojang.serialization.JsonOps.INSTANCE, condition)
                    .getOrThrow(false, message -> { throw new com.google.gson.JsonParseException(message); });
            for (var entry : encoded.getAsJsonObject().entrySet()) {
                json.add(entry.getKey(), entry.getValue());
            }
        }
        @Override
        public T deserialize(com.google.gson.JsonObject json, com.google.gson.JsonDeserializationContext context) {
            return codec.parse(com.mojang.serialization.JsonOps.INSTANCE, json)
                    .getOrThrow(false, message -> { throw new com.google.gson.JsonParseException(message); });
        }
    }
    *///?}

    private ModLootConditions() { }

    public static void init() { }
}
