package entities.impl;

import entities.BaiFile;
import models.Vector;

import java.util.Objects;

public class VertsMission implements BaiFile {

    private int zoneId;
    private Vector pos;
    private Vector dir;
    private double approxRadius;
    private byte flags;
    private byte approxHeight;

    public VertsMission() {
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertsMission that = (VertsMission) o;
        return zoneId == that.zoneId && Double.compare(that.approxRadius, approxRadius) == 0 && flags == that.flags && approxHeight == that.approxHeight && Objects.equals(pos, that.pos) && Objects.equals(dir, that.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, pos, dir, approxRadius, flags, approxHeight);
    }

    @Override
    public String toString() {
        return "VertsMission{" +
                "zoneId=" + zoneId +
                ", pos=" + pos +
                ", dir=" + dir +
                ", approxRadius=" + approxRadius +
                ", flags=" + flags +
                ", approxHeight=" + approxHeight +
                '}';
    }
}
