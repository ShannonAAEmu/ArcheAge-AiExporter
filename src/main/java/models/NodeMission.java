package models;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class NodeMission {

    @Expose()
    private final int id;

    @Expose()
    private final double x;

    @Expose()
    private final double y;

    @Expose()
    private final double z;

    public NodeMission(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return id;
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
        NodeMission nodeMission = (NodeMission) o;
        return id == nodeMission.id && Double.compare(nodeMission.x, x) == 0 && Double.compare(nodeMission.y, y) == 0 && Double.compare(nodeMission.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, z);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
