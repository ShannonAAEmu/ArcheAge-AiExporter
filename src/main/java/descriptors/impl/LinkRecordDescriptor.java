package descriptors.impl;

import descriptors.Descriptor;
import entities.LinkRecord;

import java.util.List;
import java.util.Objects;

public class LinkRecordDescriptor implements Descriptor {

    private int zoneId;
    private List<LinkRecord> linkedVolumeRecords;
    private List<LinkRecord> linkedFlightRecords;

    public LinkRecordDescriptor() {
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
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
        LinkRecordDescriptor that = (LinkRecordDescriptor) o;
        return zoneId == that.zoneId && Objects.equals(linkedVolumeRecords, that.linkedVolumeRecords) && Objects.equals(linkedFlightRecords, that.linkedFlightRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, linkedVolumeRecords, linkedFlightRecords);
    }

    @Override
    public String toString() {
        return "LinkRecordDescriptor{" +
                "zoneId=" + zoneId +
                ", linkedVolumeRecords=" + linkedVolumeRecords +
                ", linkedFlightRecords=" + linkedFlightRecords +
                '}';
    }
}
