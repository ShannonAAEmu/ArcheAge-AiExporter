package entities;

import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.Objects;

public class NodeDescriptor {

    @Expose()
    private long id;

    @Expose()
    private Vector dir;

    @Expose()
    private Vector up;

    @Expose()
    private Vector pos;

    @Expose()
    private int index;                          // building, span or volume

    @Expose()
    private int[] obstacle;

    @Expose()
    private byte type;

    @Expose()
    private byte unk1;

    @Expose()
    private byte unk2;

    @Expose()
    private byte unk3;

    public NodeDescriptor() {
        this.obstacle = new int[3];
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int[] getObstacle() {
        return obstacle;
    }

    public void setObstacle(int[] obstacle) {
        this.obstacle = obstacle;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
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

    public byte getUnk3() {
        return unk3;
    }

    public void setUnk3(byte unk3) {
        this.unk3 = unk3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeDescriptor that = (NodeDescriptor) o;
        return id == that.id && index == that.index && type == that.type && unk1 == that.unk1 && unk2 == that.unk2 && unk3 == that.unk3 && Objects.equals(dir, that.dir) && Objects.equals(up, that.up) && Objects.equals(pos, that.pos) && Arrays.equals(obstacle, that.obstacle);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, dir, up, pos, index, type, unk1, unk2, unk3);
        result = 31 * result + Arrays.hashCode(obstacle);
        return result;
    }

    @Override
    public String toString() {
        return "NodeDescriptor{" +
                "id=" + id +
                ", dir=" + dir +
                ", up=" + up +
                ", pos=" + pos +
                ", index=" + index +
                ", obstacle=" + Arrays.toString(obstacle) +
                ", type=" + type +
                ", unk1=" + unk1 +
                ", unk2=" + unk2 +
                ", unk3=" + unk3 +
                '}';
    }
}
