package entities;

import java.util.Objects;

public class RoadNode {

    private long nodeIndex;
    private Vector pos;
    private double width;
    private double offset;

    public RoadNode() {
    }

    public long getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(long nodeIndex) {
        this.nodeIndex = nodeIndex;
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
        return nodeIndex == roadNode.nodeIndex && Double.compare(roadNode.width, width) == 0 && Double.compare(roadNode.offset, offset) == 0 && Objects.equals(pos, roadNode.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeIndex, pos, width, offset);
    }

    @Override
    public String toString() {
        return "RoadNode{" +
                "nodeIndex=" + nodeIndex +
                ", pos=" + pos +
                ", width=" + width +
                ", offset=" + offset +
                '}';
    }
}
