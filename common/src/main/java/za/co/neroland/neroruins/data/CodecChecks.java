package za.co.neroland.neroruins.data;

import java.util.Objects;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;

public final class CodecChecks {
    private CodecChecks() { }

    public static <T> MapCodec<T> optionalField(Codec<T> codec, String name, T fallback) {
        //? if >=1.20.5 {
        return codec.optionalFieldOf(name, fallback);
        //?} else {
        /*return new MapCodec<>() {
            @Override
            public <O> DataResult<T> decode(DynamicOps<O> ops, MapLike<O> input) {
                O field = input.get(name);
                return field == null ? DataResult.success(fallback) : codec.parse(ops, field);
            }

            @Override
            public <O> RecordBuilder<O> encode(T value, DynamicOps<O> ops, RecordBuilder<O> prefix) {
                return Objects.equals(value, fallback) ? prefix : prefix.add(name, codec.encodeStart(ops, value));
            }

            @Override
            public <O> Stream<O> keys(DynamicOps<O> ops) {
                return Stream.of(ops.createString(name));
            }
        };
        *///?}
    }

    public static Codec<Integer> intRange(int min, int max) {
        // Older DFU range codecs allowed a partial invalid value through to record constructors.
        java.util.function.Function<Integer, DataResult<Integer>> check = value ->
                value >= min && value <= max ? DataResult.success(value)
                        : DataResult.error(() -> "Expected a value between " + min + " and " + max);
        return Codec.INT.flatXmap(check, check);
    }
}
