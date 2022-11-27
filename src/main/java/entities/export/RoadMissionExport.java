package entities.export;

import com.google.gson.annotations.Expose;
import entities.Road;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoadMissionExport {

    @Expose
    private final List<List<Road>> roadList;

    public RoadMissionExport() {
        this.roadList = new ArrayList<>();
    }

    public List<List<Road>> getRoadList() {
        return roadList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadMissionExport that = (RoadMissionExport) o;
        return Objects.equals(roadList, that.roadList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roadList);
    }

    @Override
    public String toString() {
        return "RoadMissionExport{" +
                "roadList=" + roadList +
                '}';
    }
}
