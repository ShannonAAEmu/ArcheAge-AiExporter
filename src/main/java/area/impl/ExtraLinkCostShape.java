package area.impl;

import area.Area;
import com.google.gson.annotations.Expose;
import entities.neww.AABB;
import entities.neww.Vector;

import java.util.List;
import java.util.Objects;

public class ExtraLinkCostShape implements Area {
    @Expose()
    private final int zoneId;

    @Expose()
    private String name;

    @Expose()
    private double costFactor;

    @Expose()
    private AABB aabb;

    @Expose()
    private List<Vector> points;

    public ExtraLinkCostShape(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getZoneId() {
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

    public double getCostFactor() {
        return costFactor;
    }

    public void setCostFactor(double costFactor) {
        this.costFactor = costFactor;
    }

    public AABB getAabb() {
        return aabb;
    }

    public void setAabb(AABB aabb) {
        this.aabb = aabb;
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
        ExtraLinkCostShape that = (ExtraLinkCostShape) o;
        return zoneId == that.zoneId && Double.compare(that.costFactor, costFactor) == 0 && Objects.equals(name, that.name) && Objects.equals(aabb, that.aabb) && Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, name, costFactor, aabb, points);
    }

    @Override
    public String toString() {
        return "ExtraLinkCostShape{" +
                "zoneId=" + zoneId +
                ", name='" + name + '\'' +
                ", costFactor=" + costFactor +
                ", aabb=" + aabb +
                ", points=" + points +
                '}';
    }
}
