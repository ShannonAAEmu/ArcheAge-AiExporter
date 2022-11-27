package missions.impl;

import com.google.gson.annotations.Expose;
import entities.FlightLinkDescriptor;
import entities.Span;
import missions.Mission;

import java.util.List;
import java.util.Objects;

public class FlightNavRegion implements Mission {

    @Expose()
    private final long zoneId;

    @Expose()
    private String name;

    @Expose()
    private int heightFieldOriginX;

    @Expose()
    private int heightFieldOriginY;

    @Expose()
    private int heightFieldDimX;    // The size of the coarse grid of cells.

    @Expose()
    private int heightFieldDimY;    // The size of the coarse grid of cells.

    @Expose()
    private int childSubDiv;        // If the cell is defined as hires (child idx > 0), the cell is divided based on this variable.

    @Expose()
    private int terrainDownSample;  // The down sample ratio from the terrain.

    @Expose()
    private List<Span> spanList;

    @Expose()
    private List<FlightLinkDescriptor> flightLinkDescriptorList;

    public FlightNavRegion(long zoneId) {
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

    public int getHeightFieldOriginX() {
        return heightFieldOriginX;
    }

    public void setHeightFieldOriginX(int heightFieldOriginX) {
        this.heightFieldOriginX = heightFieldOriginX;
    }

    public int getHeightFieldOriginY() {
        return heightFieldOriginY;
    }

    public void setHeightFieldOriginY(int heightFieldOriginY) {
        this.heightFieldOriginY = heightFieldOriginY;
    }

    public int getHeightFieldDimX() {
        return heightFieldDimX;
    }

    public void setHeightFieldDimX(int heightFieldDimX) {
        this.heightFieldDimX = heightFieldDimX;
    }

    public int getHeightFieldDimY() {
        return heightFieldDimY;
    }

    public void setHeightFieldDimY(int heightFieldDimY) {
        this.heightFieldDimY = heightFieldDimY;
    }

    public int getChildSubDiv() {
        return childSubDiv;
    }

    public void setChildSubDiv(int childSubDiv) {
        this.childSubDiv = childSubDiv;
    }

    public int getTerrainDownSample() {
        return terrainDownSample;
    }

    public void setTerrainDownSample(int terrainDownSample) {
        this.terrainDownSample = terrainDownSample;
    }

    public List<Span> getSpanList() {
        return spanList;
    }

    public void setSpanList(List<Span> spanList) {
        this.spanList = spanList;
    }

    public List<FlightLinkDescriptor> getFlightLinkDescriptorList() {
        return flightLinkDescriptorList;
    }

    public void setFlightLinkDescriptorList(List<FlightLinkDescriptor> flightLinkDescriptorList) {
        this.flightLinkDescriptorList = flightLinkDescriptorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightNavRegion that = (FlightNavRegion) o;
        return zoneId == that.zoneId && heightFieldOriginX == that.heightFieldOriginX && heightFieldOriginY == that.heightFieldOriginY && heightFieldDimX == that.heightFieldDimX && heightFieldDimY == that.heightFieldDimY && childSubDiv == that.childSubDiv && terrainDownSample == that.terrainDownSample && Objects.equals(name, that.name) && Objects.equals(spanList, that.spanList) && Objects.equals(flightLinkDescriptorList, that.flightLinkDescriptorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, name, heightFieldOriginX, heightFieldOriginY, heightFieldDimX, heightFieldDimY, childSubDiv, terrainDownSample, spanList, flightLinkDescriptorList);
    }

    @Override
    public String toString() {
        return "FlightNavRegion{" +
                "zoneId=" + zoneId +
                ", name='" + name + '\'' +
                ", heightFieldOriginX=" + heightFieldOriginX +
                ", heightFieldOriginY=" + heightFieldOriginY +
                ", heightFieldDimX=" + heightFieldDimX +
                ", heightFieldDimY=" + heightFieldDimY +
                ", childSubDiv=" + childSubDiv +
                ", terrainDownSample=" + terrainDownSample +
                ", spanList=" + spanList +
                ", flightLinkDescriptorList=" + flightLinkDescriptorList +
                '}';
    }
}
