package models;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Triangulation {

    @Expose()
    private final int sourceId;

    @Expose()
    private final int targetId;

    @Expose()
    private final double x;

    @Expose()
    private final double y;

    @Expose()
    private final double z;


    public Triangulation(int sourceId, int targetId, double x, double y, double z) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangulation that = (Triangulation) o;
        return sourceId == that.sourceId && targetId == that.targetId && Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, targetId, x, y, z);
    }

    @Override
    public String toString() {
        return "Triangulation{" +
                "sourceId=" + sourceId +
                ", targetId=" + targetId +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
