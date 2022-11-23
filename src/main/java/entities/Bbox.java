package entities;

import java.util.Objects;

public class Bbox {

    private Vector triangularBBoxMin;
    private Vector triangularBBoxMax;

    public Bbox() {
    }

    public Vector getTriangularBBoxMin() {
        return triangularBBoxMin;
    }

    public void setTriangularBBoxMin(Vector triangularBBoxMin) {
        this.triangularBBoxMin = triangularBBoxMin;
    }

    public Vector getTriangularBBoxMax() {
        return triangularBBoxMax;
    }

    public void setTriangularBBoxMax(Vector triangularBBoxMax) {
        this.triangularBBoxMax = triangularBBoxMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bbox bbox = (Bbox) o;
        return Objects.equals(triangularBBoxMin, bbox.triangularBBoxMin) && Objects.equals(triangularBBoxMax, bbox.triangularBBoxMax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(triangularBBoxMin, triangularBBoxMax);
    }

    @Override
    public String toString() {
        return "Bbox{" +
                "triangularBBoxMin=" + triangularBBoxMin +
                ", triangularBBoxMax=" + triangularBBoxMax +
                '}';
    }
}
