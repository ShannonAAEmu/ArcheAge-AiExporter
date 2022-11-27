package entities;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Objects;

public class WaypointSurfaceNavigation {

    @Expose()
    private final int zoneId;

    @Expose()
    private List<LinkRecord> linkedVolumeRecords;

    @Expose()
    private List<LinkRecord> linkedFlightRecords;

    public WaypointSurfaceNavigation(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public List<LinkRecord> getLinkedVolumeRecords() {
        return linkedVolumeRecords;
    }

    public void setLinkedVolumeRecords(List<LinkRecord> linkedVolumeRecords) {
        this.linkedVolumeRecords = linkedVolumeRecords;
    }

    public List<LinkRecord> getLinkedFlightRecords() {
        return linkedFlightRecords;
    }

    public void setLinkedFlightRecords(List<LinkRecord> linkedFlightRecords) {
        this.linkedFlightRecords = linkedFlightRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaypointSurfaceNavigation that = (WaypointSurfaceNavigation) o;
        return zoneId == that.zoneId && Objects.equals(linkedVolumeRecords, that.linkedVolumeRecords) && Objects.equals(linkedFlightRecords, that.linkedFlightRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, linkedVolumeRecords, linkedFlightRecords);
    }

    @Override
    public String toString() {
        return "WaypointSurfaceNavigation{" +
                "zoneId=" + zoneId +
                ", linkedVolumeRecords=" + linkedVolumeRecords +
                ", linkedFlightRecords=" + linkedFlightRecords +
                '}';
    }
}
