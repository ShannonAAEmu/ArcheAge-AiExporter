package entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class BBox {

    @Expose
    private Vector min;

    @Expose
    private Vector max;

    public BBox() {
    }

    public Vector getMin() {
        return min;
    }

    public void setMin(Vector min) {
        this.min = min;
    }

    public Vector getMax() {
        return max;
    }

    public void setMax(Vector max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BBox bBox = (BBox) o;
        return Objects.equals(min, bBox.min) && Objects.equals(max, bBox.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "BBox{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
