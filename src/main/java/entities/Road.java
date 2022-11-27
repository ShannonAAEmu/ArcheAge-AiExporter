package entities;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Objects;

public class Road {

    @Expose()
    private int zoneId;
    @Expose()
    private String name;

    @Expose()
    private List<RoadNode> roadNodeList;

    public Road() {
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
        Road road = (Road) o;
        return zoneId == road.zoneId && Objects.equals(name, road.name) && Objects.equals(roadNodeList, road.roadNodeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, name, roadNodeList);
    }

    @Override
    public String toString() {
        return "Road{" +
                "zoneId=" + zoneId +
                ", name='" + name + '\'' +
                ", roadNodeList=" + roadNodeList +
                '}';
    }
}
