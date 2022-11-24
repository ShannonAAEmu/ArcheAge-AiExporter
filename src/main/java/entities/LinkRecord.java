package entities;

import java.util.Objects;

public class LinkRecord {

    private int nodeIndex;
    private double radiusOut;
    private double radiusIn;

    public LinkRecord() {
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public double getRadiusOut() {
        return radiusOut;
    }

    public void setRadiusOut(double radiusOut) {
        this.radiusOut = radiusOut;
    }

    public double getRadiusIn() {
        return radiusIn;
    }

    public void setRadiusIn(double radiusIn) {
        this.radiusIn = radiusIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkRecord that = (LinkRecord) o;
        return nodeIndex == that.nodeIndex && Double.compare(that.radiusOut, radiusOut) == 0 && Double.compare(that.radiusIn, radiusIn) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeIndex, radiusOut, radiusIn);
    }

    @Override
    public String toString() {
        return "LinkRecord{" +
                "nodeIndex=" + nodeIndex +
                ", radiusOut=" + radiusOut +
                ", radiusIn=" + radiusIn +
                '}';
    }
}
