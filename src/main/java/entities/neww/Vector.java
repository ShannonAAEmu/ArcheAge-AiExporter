package entities.neww;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Vector {
    @Expose()
    private String name;

    @Expose()
    private double x;

    @Expose()
    private double y;

    @Expose()
    private double z;

    public Vector(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0 && Double.compare(vector.z, z) == 0 && Objects.equals(name, vector.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, z);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
