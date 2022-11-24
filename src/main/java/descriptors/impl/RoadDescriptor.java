package descriptors.impl;

import descriptors.Descriptor;
import entities.RoadNode;

import java.util.List;
import java.util.Objects;

public class RoadDescriptor implements Descriptor {

    private int zoneId;
    private String name;
    private List<RoadNode> roadNodeList;

    public RoadDescriptor() {
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoadNode> getRoadNodeList() {
        return roadNodeList;
    }

    public void setRoadNodeList(List<RoadNode> roadNodeList) {
        this.roadNodeList = roadNodeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadDescriptor that = (RoadDescriptor) o;
        return zoneId == that.zoneId && Objects.equals(name, that.name) && Objects.equals(roadNodeList, that.roadNodeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, name, roadNodeList);
    }

    @Override
    public String toString() {
        return "RoadDescriptor{" +
                "zoneId=" + zoneId +
                ", name='" + name + '\'' +
                ", roadNodeList=" + roadNodeList +
                '}';
    }
}
