package entities;

import java.util.Objects;

public class FlightNavRegion {
    private int heightFieldOriginX;
    private int heightFieldOriginY;
    private int heightFieldDimX;    // The size of the coarse grid of cells.
    private int heightFieldDimY;    // The size of the coarse grid of cells.
    private int childSubDiv;        // If the cell is defined as hires (child idx > 0), the cell is divided based on this variable.
    private int terrainDownSample;  // The down sample ratio from the terrain.

    public FlightNavRegion() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightNavRegion that = (FlightNavRegion) o;
        return heightFieldOriginX == that.heightFieldOriginX && heightFieldOriginY == that.heightFieldOriginY && heightFieldDimX == that.heightFieldDimX && heightFieldDimY == that.heightFieldDimY && childSubDiv == that.childSubDiv && terrainDownSample == that.terrainDownSample;
    }

    @Override
    public int hashCode() {
        return Objects.hash(heightFieldOriginX, heightFieldOriginY, heightFieldDimX, heightFieldDimY, childSubDiv, terrainDownSample);
    }

    @Override
    public String toString() {
        return "FlightNavRegion{" +
                "heightFieldOriginX=" + heightFieldOriginX +
                ", heightFieldOriginY=" + heightFieldOriginY +
                ", heightFieldDimX=" + heightFieldDimX +
                ", heightFieldDimY=" + heightFieldDimY +
                ", childSubDiv=" + childSubDiv +
                ", terrainDownSample=" + terrainDownSample +
                '}';
    }
}
