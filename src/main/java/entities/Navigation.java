package entities;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Objects;

public class Navigation {

    @Expose
    private final int zoneId;

    @Expose
    private BBox bBox;

    @Expose
    private List<NodeDescriptor> descriptorList;

    @Expose
    private List<LinkDescriptor> linkDescriptorList;

    public Navigation(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public BBox getbBox() {
        return bBox;
    }

    public void setbBox(BBox bBox) {
        this.bBox = bBox;
    }

    public List<NodeDescriptor> getDescriptorList() {
        return descriptorList;
    }

    public void setDescriptorList(List<NodeDescriptor> descriptorList) {
        this.descriptorList = descriptorList;
    }

    public List<LinkDescriptor> getLinkDescriptorList() {
        return linkDescriptorList;
    }

    public void setLinkDescriptorList(List<LinkDescriptor> linkDescriptorList) {
        this.linkDescriptorList = linkDescriptorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Navigation navigation = (Navigation) o;
        return zoneId == navigation.zoneId && Objects.equals(bBox, navigation.bBox) && Objects.equals(descriptorList, navigation.descriptorList) && Objects.equals(linkDescriptorList, navigation.linkDescriptorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, bBox, descriptorList, linkDescriptorList);
    }

    @Override
    public String toString() {
        return "Node{" +
                "zoneId=" + zoneId +
                ", bBox=" + bBox +
                ", descriptorList=" + descriptorList +
                ", linkDescriptorList=" + linkDescriptorList +
                '}';
    }
}
