package entities.impl;

import entities.BaiFile;
import models.Vector;

import java.util.Objects;

public class NodeDescriptor implements BaiFile {

    private final Vector triangularBBoxMin;
    private final Vector triangularBBoxMax;
    private int zoneId;
    private int id;
    private Vector dir;
    private Vector up;
    private Vector pos;
    private int index;      // building, span or volume
    private int obstacle1;
    private int obstacle2;
    private int obstacle3;
    private EWaypointNodeType type;      // 0-4 from EWaypointNodeType
    private byte forbidden;
    private byte unk1;
    private byte unk2;

    public NodeDescriptor(Vector triangularBBoxMin, Vector triangularBBoxMax) {
        this.triangularBBoxMin = triangularBBoxMin;
        this.triangularBBoxMax = triangularBBoxMax;
    }

    public Vector getTriangularBBoxMin() {
        return triangularBBoxMin;
    }

    public Vector getTriangularBBoxMax() {
        return triangularBBoxMax;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector getDir() {
        return dir;
    }

    public void setDir(Vector dir) {
        this.dir = dir;
    }

    public Vector getUp() {
        return up;
    }

    public void setUp(Vector up) {
        this.up = up;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getObstacle1() {
        return obstacle1;
    }

    public void setObstacle1(int obstacle1) {
        this.obstacle1 = obstacle1;
    }

    public int getObstacle2() {
        return obstacle2;
    }

    public void setObstacle2(int obstacle2) {
        this.obstacle2 = obstacle2;
    }

    public int getObstacle3() {
        return obstacle3;
    }

    public void setObstacle3(int obstacle3) {
        this.obstacle3 = obstacle3;
    }

    public EWaypointNodeType getType() {
        return type;
    }

    public void setType(EWaypointNodeType type) {
        this.type = type;
    }

    public byte getForbidden() {
        return forbidden;
    }

    public void setForbidden(byte forbidden) {
        this.forbidden = forbidden;
    }

    public byte getUnk1() {
        return unk1;
    }

    public void setUnk1(byte unk1) {
        this.unk1 = unk1;
    }

    public byte getUnk2() {
        return unk2;
    }

    public void setUnk2(byte unk2) {
        this.unk2 = unk2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeDescriptor that = (NodeDescriptor) o;
        return zoneId == that.zoneId && id == that.id && index == that.index && obstacle1 == that.obstacle1 && obstacle2 == that.obstacle2 && obstacle3 == that.obstacle3 && forbidden == that.forbidden && unk1 == that.unk1 && unk2 == that.unk2 && Objects.equals(triangularBBoxMin, that.triangularBBoxMin) && Objects.equals(triangularBBoxMax, that.triangularBBoxMax) && Objects.equals(dir, that.dir) && Objects.equals(up, that.up) && Objects.equals(pos, that.pos) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(triangularBBoxMin, triangularBBoxMax, zoneId, id, dir, up, pos, index, obstacle1, obstacle2, obstacle3, type, forbidden, unk1, unk2);
    }

    @Override
    public String toString() {
        return "NodeDescriptor{" +
                "triangularBBoxMin=" + triangularBBoxMin +
                ", triangularBBoxMax=" + triangularBBoxMax +
                ", zoneId=" + zoneId +
                ", id=" + id +
                ", dir=" + dir +
                ", up=" + up +
                ", pos=" + pos +
                ", index=" + index +
                ", obstacle1=" + obstacle1 +
                ", obstacle2=" + obstacle2 +
                ", obstacle3=" + obstacle3 +
                ", type=" + type +
                ", forbidden=" + forbidden +
                ", unk1=" + unk1 +
                ", unk2=" + unk2 +
                '}';
    }

    public enum EWaypointNodeType {
        WNT_UNSET,
        WNT_WAYPOINT,
        WNT_HIDE,
        WNT_ENTRYEXIT,
        WNT_EXITONLY,
        WNT_HIDESECONDARY
    }
}
