package de.ambertation.lib.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class Float3 {
    public static final double EPSILON = 1.e-10;
    public static final Float3 ZERO = Float3.of(0);
    public static final Float3 X_AXIS = Float3.of(1, 0, 0);
    public static final Float3 Y_AXIS = Float3.of(0, 1, 0);
    public static final Float3 Z_AXIS = Float3.of(0, 0, 1);

    public static final Float3[] BLOCK_CORNER_OFFSETS = {
            Float3.of(0.5, 0.5, 0.5),
            Float3.of(0.5, -0.5, 0.5),
            Float3.of(-0.5, -0.5, 0.5),
            Float3.of(-0.5, 0.5, 0.5),
            Float3.of(0.5, 0.5, -0.5),
            Float3.of(0.5, -0.5, -0.5),
            Float3.of(-0.5, -0.5, -0.5),
            Float3.of(-0.5, 0.5, -0.5)
    };

    public static final Codec<Float3> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.FLOAT.fieldOf("x").forGetter(o -> (float) o.x),
                    Codec.FLOAT.fieldOf("y").forGetter(o -> (float) o.y),
                    Codec.FLOAT.fieldOf("z").forGetter(o -> (float) o.z)
            )
            .apply(instance, Float3::new)
    );
    public final double x;
    public final double y;
    public final double z;

    public Float3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Float3 of(double xyz) {
        return new Float3(xyz, xyz, xyz);
    }

    public static Float3 of(double x, double y, double z) {
        return new Float3(x, y, z);
    }

    public static Float3 of(Float2 xy, double z) {
        return new Float3(xy.x, xy.y, z);
    }

    public static Float3 of(double x, Float2 xy) {
        return new Float3(x, xy.x, xy.y);
    }

    public static Float3 of(BlockPos pos) {
        return new Float3(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Float3 of(Vec3 pos) {
        return new Float3(pos.x(), pos.y(), pos.z());
    }

    public static Float3 blockAligned(double x, double y, double z) {
        return new Float3(toBlockPos(x), toBlockPos(y), toBlockPos(z));
    }

    public Float3 blockAligned() {
        return new Float3(toBlockPos(x), toBlockPos(y), toBlockPos(z));
    }

    public Float3 div(Float3 p) {
        return new Float3(x / p.x, y / p.y, z / p.z);
    }

    public Float3 mul(Float3 p) {
        return new Float3(x * p.x, y * p.y, z * p.z);
    }

    public double dot(Float3 p) {
        return x * p.x + y * p.y + z * p.z;
    }

    public Float3 cross(Float3 p) {
        return Float3.of(y * p.z - z * p.y, z * p.x - x * p.z, x * p.y - y * p.x);
    }

    public Float3 square() {
        return new Float3(x * x, y * y, z * z);
    }

    public Float3 add(Float3 p) {
        return new Float3(x + p.x, y + p.y, z + p.z);
    }

    public Float3 add(double d) {
        return new Float3(x + d, y + d, z + d);
    }

    public Float3 sub(double d) {
        return new Float3(x - d, y - d, z - d);
    }

    public Float3 abs() {
        return new Float3(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Float3 max(double d) {
        return new Float3(Math.max(x, d), Math.max(y, d), Math.max(z, d));
    }

    public Float3 min(double d) {
        return new Float3(Math.min(x, d), Math.min(y, d), Math.min(z, d));
    }

    public Float3 sub(Float3 p) {
        return new Float3(x - p.x, y - p.y, z - p.z);
    }

    public Float3 mul(double d) {
        return new Float3(x * d, y * d, z * d);
    }

    public Float3 div(double d) {
        return new Float3(x / d, y / d, z / d);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lengthSquare() {
        return x * x + y * y + z * z;
    }

    public Float3 normalized() {
        double d = length();
        return new Float3(x / d, y / d, z / d);
    }

    public AABB toAABB() {
        return new AABB(x, y, z, x + 1, y + 1, z + 1);
    }

    public AABB toAABB(Vec3 offset) {
        return new AABB(x + offset.x, y + offset.y, z + offset.z, x + 1 + offset.x, y + 1 + offset.y, z + 1 + offset.z);
    }

    public BlockPos toBlockPos() {
        return toBlockPos(x, y, z);
    }

    public Float3 ceil() {
        return of(Math.ceil(x), Math.ceil(y), Math.ceil(z));
    }

    public Float3 floor() {
        return of(Math.floor(x), Math.floor(y), Math.floor(z));
    }

    public Float3 round() {
        return of(Math.round(x), Math.round(y), Math.round(z));
    }

    public Float3 conservative() {
        return of(conservative(x), conservative(y), conservative(z));
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }


    public double distSquare(Float3 b) {
        return Math.pow(x - b.x, 2) +
                Math.pow(y - b.y, 2) +
                Math.pow(z - b.z, 2);
    }

    public double distSquare(BlockPos b) {
        return Math.pow(x - b.getX(), 2) +
                Math.pow(y - b.getY(), 2) +
                Math.pow(z - b.getZ(), 2);
    }

    public Float3 rotate(Quaternion rot) {
        return rot.rotate(this);
    }

    public Float3 rotateX(double a) {
        return Float3.of(
                x * Math.cos(a) - y * Math.sin(a),
                x * Math.sin(a) + y * Math.cos(a),
                z
        );
    }

    public Float3 rotateY(double a) {
        return Float3.of(
                x * Math.cos(a) + z * Math.sin(a),
                y,
                -x * Math.sin(a) + z * Math.cos(a)
        );
    }

    public Float3 rotateZ(double a) {
        return Float3.of(
                x,
                y * Math.cos(a) - z * Math.sin(a),
                y * Math.sin(a) + z * Math.cos(a)
        );
    }

    public static double conservative(double x) {
        return x < 0 ? Math.floor(x) : Math.ceil(x);
    }

    public static double toBlockPos(double d) {
        return (int) Math.round(d + 0.5) - 1;
    }

    public static BlockPos toBlockPos(Vec3 vec) {
        return toBlockPos(vec.x, vec.y, vec.z);
    }

    public static BlockPos toBlockPos(double x, double y, double z) {
        return new BlockPos(
                (int) Math.round(x + 0.5) - 1,
                (int) Math.round(y + 0.5) - 1,
                (int) Math.round(z + 0.5) - 1
        );
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Float3 pos = (Float3) o;
        return Math.abs(pos.x - x) < EPSILON
                && Math.abs(pos.y - y) < EPSILON
                && Math.abs(pos.z - z) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }


    public void serializeToNetwork(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public static Float3 deserializeFromNetwork(FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return Float3.of(x, y, z);
    }

    //-------------------------------------------- SWIVELS (@formatter:off) --------------------------------------------
    //Scalar -----------
    public double x() { return x; }
    public double y() { return y; }
    public double z() { return z; }


    //2D -----------
    public Float2 xx() { return new Float2(x, x); }
    public Float2 xy() { return new Float2(x, y); }
    public Float2 xz() { return new Float2(x, z); }
    public Float2 yx() { return new Float2(y, x); }
    public Float2 yy() { return new Float2(y, y); }
    public Float2 yz() { return new Float2(y, z); }
    public Float2 zx() { return new Float2(z, x); }
    public Float2 zy() { return new Float2(z, y); }
    public Float2 zz() { return new Float2(z, z); }


    //3D -----------
    public Float3 xxx() { return new Float3(x, x, x); }
    public Float3 xxy() { return new Float3(x, x, y); }
    public Float3 xxz() { return new Float3(x, x, z); }
    public Float3 xyx() { return new Float3(x, y, x); }
    public Float3 xyy() { return new Float3(x, y, y); }
    public Float3 xyz() { return new Float3(x, y, z); }
    public Float3 xzx() { return new Float3(x, z, x); }
    public Float3 xzy() { return new Float3(x, z, y); }
    public Float3 xzz() { return new Float3(x, z, z); }
    public Float3 yxx() { return new Float3(y, x, x); }
    public Float3 yxy() { return new Float3(y, x, y); }
    public Float3 yxz() { return new Float3(y, x, z); }
    public Float3 yyx() { return new Float3(y, y, x); }
    public Float3 yyy() { return new Float3(y, y, y); }
    public Float3 yyz() { return new Float3(y, y, z); }
    public Float3 yzx() { return new Float3(y, z, x); }
    public Float3 yzy() { return new Float3(y, z, y); }
    public Float3 yzz() { return new Float3(y, z, z); }
    public Float3 zxx() { return new Float3(z, x, x); }
    public Float3 zxy() { return new Float3(z, x, y); }
    public Float3 zxz() { return new Float3(z, x, z); }
    public Float3 zyx() { return new Float3(z, y, x); }
    public Float3 zyy() { return new Float3(z, y, y); }
    public Float3 zyz() { return new Float3(z, y, z); }
    public Float3 zzx() { return new Float3(z, z, x); }
    public Float3 zzy() { return new Float3(z, z, y); }
    public Float3 zzz() { return new Float3(z, z, z); }
    //-------------------------------------------- SWIVELS  (@formatter:on) --------------------------------------------
}
