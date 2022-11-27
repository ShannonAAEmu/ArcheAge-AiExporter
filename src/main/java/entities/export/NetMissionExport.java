package entities.export;

import com.google.gson.annotations.Expose;
import entities.Navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NetMissionExport {

    @Expose
    private final List<Navigation> navigations;

    public NetMissionExport() {
        this.navigations = new ArrayList<>();
    }

    public List<Navigation> getNavigations() {
        return navigations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetMissionExport that = (NetMissionExport) o;
        return Objects.equals(navigations, that.navigations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(navigations);
    }

    @Override
    public String toString() {
        return "NetMissionExport{" +
                "navigations=" + navigations +
                '}';
    }
}
