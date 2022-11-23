package descriptors.impl.links;

import descriptors.Descriptor;

import java.util.Objects;

public class FlightLinkDescriptor implements Descriptor {

    private int startIdx;
    private int endIdx;

    public FlightLinkDescriptor() {
    }

    public int getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
    }

    public int getEndIdx() {
        return endIdx;
    }

    public void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightLinkDescriptor that = (FlightLinkDescriptor) o;
        return startIdx == that.startIdx && endIdx == that.endIdx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startIdx, endIdx);
    }

    @Override
    public String toString() {
        return "FlightLinkDescriptor{" +
                "startIdx=" + startIdx +
                ", endIdx=" + endIdx +
                '}';
    }
}
