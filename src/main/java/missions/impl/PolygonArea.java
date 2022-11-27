package missions.impl;

import missions.Mission;
import com.google.gson.annotations.Expose;
import entities.Vector;

import java.util.List;
import java.util.Objects;

public class PolygonArea implements Mission {
    @Expose()
    private final int zoneId;

    @Expose()
    private String name;

    @Expose()
    private List<Vector> points;

    @Expose()
    private NavigationType navigationType;

    @Expose()
    private int type;

    @Expose()
    private double height;

    @Expose()
    private SpecialArea.AILightLevel aiLightLevel;

    public PolygonArea(int zoneId) {
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

    public List<Vector> getPoints() {
        return points;
    }

    public void setPoints(List<Vector> points) {
        this.points = points;
    }

    public NavigationType getNavigationType() {
        return navigationType;
    }

    public void setNavigationType(NavigationType navigationType) {
        this.navigationType = navigationType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public SpecialArea.AILightLevel getAiLightLevel() {
        return aiLightLevel;
    }

    public void setAiLightLevel(SpecialArea.AILightLevel aiLightLevel) {
        this.aiLightLevel = aiLightLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolygonArea that = (PolygonArea) o;
        return zoneId == that.zoneId && type == that.type && Double.compare(that.height, height) == 0 && Objects.equals(name, that.name) && Objects.equals(points, that.points) && navigationType == that.navigationType && aiLightLevel == that.aiLightLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, name, points, navigationType, type, height, aiLightLevel);
    }

    @Override
    public String toString() {
        return "PolygonArea{" +
                "zoneId=" + zoneId +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", navigationType=" + navigationType +
                ", type=" + type +
                ", height=" + height +
                ", aiLightLevel=" + aiLightLevel +
                '}';
    }

    public enum NavigationType {
        UNSET,
        TRIANGULAR,
        WAYPOINT_HUMAN,
        WAYPOINT_3D_SURFACE,
        FLIGHT,
        VOLUME,
        ROAD,
        SMART_OBJECT,
        FREE_2D,
        LAYERED_NAV_MESH,
        CUSTOM_NAVIGATION,
        MAX_VALUE
    }
}
