package entities.export;

import com.google.gson.annotations.Expose;
import missions.impl.FlightNavRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlightMissionExport {

    @Expose
    private final List<FlightNavRegion> flightNavRegion;

    public FlightMissionExport() {
        this.flightNavRegion = new ArrayList<>();
    }

    public List<FlightNavRegion> getFlightNavRegion() {
        return flightNavRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightMissionExport that = (FlightMissionExport) o;
        return Objects.equals(flightNavRegion, that.flightNavRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNavRegion);
    }

    @Override
    public String toString() {
        return "FlightMissionExport{" +
                "flightNavRegion=" + flightNavRegion +
                '}';
    }
}
