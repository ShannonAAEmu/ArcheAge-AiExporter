package entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class FlightLinkDescriptor {

    @Expose()
    private int indexFirst;

    @Expose()
    private int indexSecond;

    public FlightLinkDescriptor() {
    }

    public int getIndexFirst() {
        return indexFirst;
    }

    public void setIndexFirst(int indexFirst) {
        this.indexFirst = indexFirst;
    }

    public int getIndexSecond() {
        return indexSecond;
    }

    public void setIndexSecond(int indexSecond) {
        this.indexSecond = indexSecond;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightLinkDescriptor that = (FlightLinkDescriptor) o;
        return indexFirst == that.indexFirst && indexSecond == that.indexSecond;
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexFirst, indexSecond);
    }

    @Override
    public String toString() {
        return "FlightLinkDescriptor{" +
                "indexFirst=" + indexFirst +
                ", indexSecond=" + indexSecond +
                '}';
    }
}
