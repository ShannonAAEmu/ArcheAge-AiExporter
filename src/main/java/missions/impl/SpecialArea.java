package missions.impl;

import com.google.gson.annotations.Expose;
import entities.Vector;
import missions.Mission;

import java.util.List;
import java.util.Objects;

public class SpecialArea implements Mission {

    @Expose()
    private final int zoneId;

    @Expose()
    private String name;

    @Expose()
    private Type type;

    @Expose()
    private WaypointConnections waypointConnections;

    @Expose()
    private boolean altered;    // making links unpassible

    @Expose()
    private double height;

    @Expose()
    private double nodeAutoConnectDistance;

    @Expose()
    private double maxZ;

    @Expose()
    private double minZ;

    @Expose()
    private int buildingID;

    @Expose()
    private AILightLevel aiLightLevel;

    @Expose()
    private List<Vector> points;

    public SpecialArea(int zoneId) {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public WaypointConnections getWaypointConnections() {
        return waypointConnections;
    }

    public void setWaypointConnections(WaypointConnections waypointConnections) {
        this.waypointConnections = waypointConnections;
    }

    public boolean isAltered() {
        return altered;
    }

    public void setAltered(boolean altered) {
        this.altered = altered;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getNodeAutoConnectDistance() {
        return nodeAutoConnectDistance;
    }

    public void setNodeAutoConnectDistance(double nodeAutoConnectDistance) {
        this.nodeAutoConnectDistance = nodeAutoConnectDistance;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public AILightLevel getAiLightLevel() {
        return aiLightLevel;
    }

    public void setAiLightLevel(AILightLevel aiLightLevel) {
        this.aiLightLevel = aiLightLevel;
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
        SpecialArea that = (SpecialArea) o;
        return zoneId == that.zoneId && altered == that.altered && Double.compare(that.height, height) == 0 && Double.compare(that.nodeAutoConnectDistance, nodeAutoConnectDistance) == 0 && Double.compare(that.maxZ, maxZ) == 0 && Double.compare(that.minZ, minZ) == 0 && buildingID == that.buildingID && Objects.equals(name, that.name) && type == that.type && waypointConnections == that.waypointConnections && aiLightLevel == that.aiLightLevel && Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, name, type, waypointConnections, altered, height, nodeAutoConnectDistance, maxZ, minZ, buildingID, aiLightLevel, points);
    }

    @Override
    public String toString() {
        return "SpecialArea{" +
                "zoneId=" + zoneId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", waypointConnections=" + waypointConnections +
                ", altered=" + altered +
                ", height=" + height +
                ", nodeAutoConnectDistance=" + nodeAutoConnectDistance +
                ", maxZ=" + maxZ +
                ", minZ=" + minZ +
                ", buildingID=" + buildingID +
                ", aiLightLevel=" + aiLightLevel +
                ", points=" + points +
                '}';
    }

    public enum Type {
        TYPE_WAYPOINT_HUMAN,
        TYPE_VOLUME,
        TYPE_FLIGHT,
        TYPE_WATER,
        TYPE_WAYPOINT_3D_SURFACE,
        TYPE_FREE_2D,
        TYPE_TRIANGULATION,
        TYPE_LAYERED_NAV_MESH
    }

    public enum WaypointConnections {
        DESIGNER_NONE,
        DESIGNER_PARTIAL,
        AUTO_NONE,
        AUTO_PARTIAL,
        MAXVALUE
    }

    public enum AILightLevel {
        NONE,
        LIGHT,
        MEDIUM,
        DARK,
        LAST
    }
}
