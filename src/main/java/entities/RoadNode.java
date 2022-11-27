package entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class RoadNode {

    @Expose()
    private Vector pos;

    @Expose()
    private double width;

    @Expose()
    private double offset;

    public RoadNode() {
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadNode roadNode = (RoadNode) o;
        return Double.compare(roadNode.width, width) == 0 && Double.compare(roadNode.offset, offset) == 0 && Objects.equals(pos, roadNode.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, width, offset);
    }

    @Override
    public String toString() {
        return "RoadNode{" +
                "pos=" + pos +
                ", width=" + width +
                ", offset=" + offset +
                '}';
    }
}
