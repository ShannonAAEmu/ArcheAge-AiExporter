package entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class ObstacleDataDescriptor {

    @Expose
    private final int zoneId;
    @Expose()
    private Vector pos;

    @Expose()
    private Vector dir;

    @Expose()
    private double approxRadius;

    @Expose()
    private byte flags;

    @Expose()
    private byte approxHeight;

    @Expose()
    private byte unk1;

    @Expose()
    private byte unk2;

    public ObstacleDataDescriptor(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public Vector getDir() {
        return dir;
    }

    public void setDir(Vector dir) {
        this.dir = dir;
    }

    public double getApproxRadius() {
        return approxRadius;
    }

    public void setApproxRadius(double approxRadius) {
        this.approxRadius = approxRadius;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public byte getApproxHeight() {
        return approxHeight;
    }

    public void setApproxHeight(byte approxHeight) {
        this.approxHeight = approxHeight;
    }

    public byte getUnk1() {
        return unk1;
    }

    public void setUnk1(byte unk1) {
        this.unk1 = unk1;
    }

    public byte getUnk2() {
        return unk2;
    }

    public void setUnk2(byte unk2) {
        this.unk2 = unk2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObstacleDataDescriptor that = (ObstacleDataDescriptor) o;
        return Double.compare(that.approxRadius, approxRadius) == 0 && flags == that.flags && approxHeight == that.approxHeight && unk1 == that.unk1 && unk2 == that.unk2 && Objects.equals(pos, that.pos) && Objects.equals(dir, that.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, dir, approxRadius, flags, approxHeight, unk1, unk2);
    }

    @Override
    public String toString() {
        return "ObstacleDataDescriptor{" +
                "pos=" + pos +
                ", dir=" + dir +
                ", approxRadius=" + approxRadius +
                ", flags=" + flags +
                ", approxHeight=" + approxHeight +
                ", unk1=" + unk1 +
                ", unk2=" + unk2 +
                '}';
    }
}
