package models;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AreasMission {

    @Expose()
    private String type;

    @Expose()
    private String layer;

    @Expose()
    private String name;

    @Expose()
    private boolean isClosed = true;

    @Expose()
    private int points;

    @Expose()
    private List<PointPos> pointsCoords;

    public AreasMission() {
        this.pointsCoords = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String name) {
        if (name.contains("ForbiddenArea") || NumberUtils.isDigits(name)) {
            this.type = "ForbiddenArea";
            this.layer = "forbidden_area";
        } else if (name.contains("ForbiddenBoundary")) {
            this.type = "ForbiddenBoundary";
            this.layer = "forbidden_boundary";
        } else if (name.contains("AINavigationModifier")) {
            this.type = "AINavigationModifier";
            this.layer = "ai_nav";
        } else if (name.contains("AIPath") || name.contains("aipath") || name.contains("path") ||
                name.contains("move") || name.contains("run") || name.contains("walk")) {
            this.type = "AIPath";
            this.layer = "ai_path";
        } else {
            System.out.println("UNKNOWN_TYPE: " + name);
            this.type = "AIPath";
            this.layer = "ai_path";
        }
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        this.isClosed = closed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<PointPos> getPointsCoords() {
        return pointsCoords;
    }

    public void setPointsCoords(List<PointPos> pointsCoords) {
        this.pointsCoords = pointsCoords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreasMission that = (AreasMission) o;
        return isClosed == that.isClosed && points == that.points && Objects.equals(type, that.type) && Objects.equals(layer, that.layer) && Objects.equals(name, that.name) && Objects.equals(pointsCoords, that.pointsCoords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, layer, name, isClosed, points, pointsCoords);
    }

    @Override
    public String toString() {
        return "AreasMission{" +
                "Type='" + type + '\'' +
                ", LAYER='" + layer + '\'' +
                ", Name='" + name + '\'' +
                ", Closed=" + isClosed +
                ", points=" + points +
                ", pointsCoords=" + pointsCoords +
                '}';
    }
}
