package org.wunder.lib.math.sdf.shapes;

import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;

import org.wunder.lib.math.Bounds;
import org.wunder.lib.math.Float3;
import org.wunder.lib.math.Transform;
import org.wunder.lib.math.sdf.SDF;

public class Empty extends SDF {
    public static final Codec<Empty> DIRECT_CODEC = Codec.unit(Empty::new);
    public static final KeyDispatchDataCodec<Empty> CODEC = KeyDispatchDataCodec.of(DIRECT_CODEC);

    public Empty() {
        super(0);
    }

    @Override
    public KeyDispatchDataCodec<? extends SDF> codec() {
        return CODEC;
    }


    //-------------------------------------------------------------------------------
    @Override
    public double dist(Float3 pos) {
        return Double.MAX_VALUE;
    }

    @Override
    public String toString() {
        return "Empty" + " [" + graphIndex + "]";
    }


    @Override
    public Bounds getBoundingBox() {
        return Bounds.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Transform defaultTransform() {
        return Transform.IDENTITY;
    }
}
