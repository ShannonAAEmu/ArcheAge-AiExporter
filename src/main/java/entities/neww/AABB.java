package entities.neww;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class AABB {

    @Expose()
    private Vector min;

    @Expose()
    private Vector max;

    public AABB() {
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
        AABB aabb = (AABB) o;
        return Objects.equals(min, aabb.min) && Objects.equals(max, aabb.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "AABB{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
