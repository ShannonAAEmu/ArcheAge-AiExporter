package entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Span {

    @Expose()
    private double x;               // Location of the span.

    @Expose()
    private double y;               // Location of the span.

    @Expose()
    private double minZ;            // Minimum height of the span.

    @Expose()
    private double maxZ;            // Maximum height of the span.

    @Expose()
    private double maxRadius;       // Max radius of a sphere the span can contain.

    @Expose()
    private int classification;     // Flag indicating that the span should be stored in hires (used in precalc).

    @Expose()
    private int childIdx;

    @Expose()
    private int nextIdx;

    public Span() {
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
        Span span = (Span) o;
        return Double.compare(span.x, x) == 0 && Double.compare(span.y, y) == 0 && Double.compare(span.minZ, minZ) == 0 && Double.compare(span.maxZ, maxZ) == 0 && Double.compare(span.maxRadius, maxRadius) == 0 && classification == span.classification && childIdx == span.childIdx && nextIdx == span.nextIdx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, minZ, maxZ, maxRadius, classification, childIdx, nextIdx);
    }

    @Override
    public String toString() {
        return "Span{" +
                "x=" + x +
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
