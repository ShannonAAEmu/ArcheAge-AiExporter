package entities.export;

import com.google.gson.annotations.Expose;
import entities.ObstacleDataDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VertexMissionExport {

    @Expose
    private final List<List<ObstacleDataDescriptor>> obstacleDataDescriptorList;

    public VertexMissionExport() {
        this.obstacleDataDescriptorList = new ArrayList<>();
    }

    public List<List<ObstacleDataDescriptor>> getObstacleDataDescriptorList() {
        return obstacleDataDescriptorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexMissionExport that = (VertexMissionExport) o;
        return Objects.equals(obstacleDataDescriptorList, that.obstacleDataDescriptorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(obstacleDataDescriptorList);
    }

    @Override
    public String toString() {
        return "VertexMissionExport{" +
                "obstacleDataDescriptorList=" + obstacleDataDescriptorList +
                '}';
    }
}
