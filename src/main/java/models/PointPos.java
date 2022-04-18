package models;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class PointPos {

    @Expose()
    private final double x;

    @Expose()
    private final double y;

    @Expose()
    private final double z;

    public PointPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
        PointPos pointPos = (PointPos) o;
        return Double.compare(pointPos.x, x) == 0 && Double.compare(pointPos.y, y) == 0 && Double.compare(pointPos.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "PointPos{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
