package missions.impl;

import com.google.gson.annotations.Expose;
import entities.Vector;
import missions.Mission;

import java.util.List;
import java.util.Objects;

public class AIShape implements Mission {
    @Expose()
    private final long zoneId;

    @Expose()
    private String name;

    @Expose()
    private List<Vector> points;

    public AIShape(int zoneId) {
        this.zoneId = zoneId;
    }

    public long getZoneId() {
        return zoneId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<Vector> getPoints() {
        return points;
    }

    public void setPoints(List<Vector> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AIShape aiShape = (AIShape) o;
        return zoneId == aiShape.zoneId && Objects.equals(name, aiShape.name) && Objects.equals(points, aiShape.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, name, points);
    }

    @Override
    public String toString() {
        return "AIShape{" +
                "zoneId=" + zoneId +
                ", name='" + name + '\'' +
                ", points=" + points +
                '}';
    }
}
