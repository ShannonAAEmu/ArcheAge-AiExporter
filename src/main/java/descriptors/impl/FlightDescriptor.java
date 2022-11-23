package descriptors.impl;

import descriptors.Descriptor;
import entities.FlightNavRegion;

import java.util.Objects;

public class FlightDescriptor implements Descriptor {

    private int zoneId;
    private FlightNavRegion flightNavRegion;
    private double x;
    private double y;
    private double minZ;
    private double maxZ;
    private double maxRadius;
    private int classification;
    private int childIdx;
    private int nextIdx;

    public FlightDescriptor() {
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public FlightNavRegion getFlightNavRegion() {
        return flightNavRegion;
    }

    public void setFlightNavRegion(FlightNavRegion flightNavRegion) {
        this.flightNavRegion = flightNavRegion;
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

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public int getChildIdx() {
        return childIdx;
    }

    public void setChildIdx(int childIdx) {
        this.childIdx = childIdx;
    }

    public int getNextIdx() {
        return nextIdx;
    }

    public void setNextIdx(int nextIdx) {
        this.nextIdx = nextIdx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDescriptor that = (FlightDescriptor) o;
        return zoneId == that.zoneId && Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.minZ, minZ) == 0 && Double.compare(that.maxZ, maxZ) == 0 && Double.compare(that.maxRadius, maxRadius) == 0 && classification == that.classification && childIdx == that.childIdx && nextIdx == that.nextIdx && Objects.equals(flightNavRegion, that.flightNavRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, flightNavRegion, x, y, minZ, maxZ, maxRadius, classification, childIdx, nextIdx);
    }

    @Override
    public String toString() {
        return "FlightDescriptor{" +
                "zoneId=" + zoneId +
                ", flightNavRegion=" + flightNavRegion +
                ", x=" + x +
                ", y=" + y +
                ", minZ=" + minZ +
                ", maxZ=" + maxZ +
                ", maxRadius=" + maxRadius +
                ", classification=" + classification +
                ", childIdx=" + childIdx +
                ", nextIdx=" + nextIdx +
                '}';
    }
}
