package de.ambertation.lib.math.sdf;

import de.ambertation.lib.math.Float3;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;

public class SDFUnion extends SDFBinaryOperation {
    public static final Codec<SDFUnion> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    SDF.CODEC.fieldOf("sdf_a").forGetter(b -> b.getFirst()),
                    SDF.CODEC.fieldOf("sdf_b").forGetter(b -> b.getSecond())
            )
            .apply(instance, SDFUnion::new)
    );

    public static final KeyDispatchDataCodec<SDFUnion> CODEC = KeyDispatchDataCodec.of(DIRECT_CODEC);

    @Override
    public KeyDispatchDataCodec<? extends SDF> codec() {
        return CODEC;
    }


    //-------------------------------------------------------------------------------
    public SDFUnion(SDF a, SDF b) {
        super(a, b);
    }

    @Override
    public double dist(Float3 pos) {
        return Math.min(getFirst().dist(pos), getSecond().dist(pos));
    }

    @Override
    public String toString() {
        return "(" + getFirst() + " | " + getSecond() + ")";
    }
}
