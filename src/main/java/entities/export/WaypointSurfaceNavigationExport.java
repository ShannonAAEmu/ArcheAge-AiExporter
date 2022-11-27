package entities.export;

import com.google.gson.annotations.Expose;
import entities.WaypointSurfaceNavigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WaypointSurfaceNavigationExport {

    @Expose
    private final List<List<WaypointSurfaceNavigation>> waypointSurfaceNavigationList;

    public WaypointSurfaceNavigationExport() {
        this.waypointSurfaceNavigationList = new ArrayList<>();
    }

    public List<List<WaypointSurfaceNavigation>> getWaypointSurfaceNavigationList() {
        return waypointSurfaceNavigationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaypointSurfaceNavigationExport that = (WaypointSurfaceNavigationExport) o;
        return Objects.equals(waypointSurfaceNavigationList, that.waypointSurfaceNavigationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waypointSurfaceNavigationList);
    }

    @Override
    public String toString() {
        return "WaypointSurfaceNavigationExport{" +
                "waypointSurfaceNavigationList=" + waypointSurfaceNavigationList +
                '}';
    }
}
