package models;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Triangulation {

    @Expose()
    private final int startPoint;

    @Expose()
    private final int endPoint;

    @Expose()
    private final double x;

    @Expose()
    private final double y;

    @Expose()
    private final double z;


    public Triangulation(int startPoint, int endPoint, double x, double y, double z) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public int getEndPoint() {
        return endPoint;
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
        return startPoint == that.startPoint && endPoint == that.endPoint && Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPoint, endPoint, x, y, z);
    }

    @Override
    public String toString() {
        return "Triangulation{" +
                "sourceId=" + startPoint +
                ", targetId=" + endPoint +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
