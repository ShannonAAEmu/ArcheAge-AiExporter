package reader.impl;

import entities.impl.VertsMission;
import models.Vector;
import reader.BaiReader;
import reader.ReaderUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VertsMissionReaderImpl implements BaiReader, Closeable {
    private static final int BAI_VERTEX_FILE_VERSION = 2;
    private final File rawBaiFile;
    private final List<VertsMission> vertsMissionList;
    private final int zoneId;
    private final File rootFolder;
    private ReaderUtil readerUtil;

    public VertsMissionReaderImpl(File rawBaiFile, int zoneId, File rootFolder) {
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.rootFolder = rootFolder;
        this.vertsMissionList = new ArrayList<>();
    }

    @Override
    public void read() {
        try {
            initReaderUtil();
            int version = readFileVersion();
            checkVersion(version);
            int descriptorsSize = readDescriptorsSize();
            assert 0 < descriptorsSize;
            readDescriptors(descriptorsSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            readerUtil.closeRAF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print() {
        System.out.println(vertsMissionList);
    }

    @Override
    public void save() {

    }

    private void initReaderUtil() throws FileNotFoundException {
        readerUtil = new ReaderUtil(new RandomAccessFile(rawBaiFile, "r"));
    }

    private int readFileVersion() throws IOException {
        return readerUtil.readInt();
    }

    private int readDescriptorsSize() throws IOException {
        return readerUtil.readInt();
    }

    private void readDescriptors(int size) throws IOException {
        double x;
        double y;
        double z;
        double approxRadius;
        byte flags;
        byte approxHeight;
        Vector pos;
        Vector dir;
        for (int i = 0; i < size; i++) {
            x = readerUtil.readDouble();
            y = readerUtil.readDouble();
            z = readerUtil.readDouble();
            pos = new Vector(x, y, z);
            x = readerUtil.readDouble();
            y = readerUtil.readDouble();
            z = readerUtil.readDouble();
            dir = new Vector(x, y, z);
            approxRadius = readerUtil.readDouble();
            flags = readerUtil.readByte();
            approxHeight = readerUtil.readByte();
            readerUtil.readShortSeparator();
            VertsMission vertsMission = new VertsMission();
            vertsMission.setZoneId(zoneId);
            vertsMission.setPos(pos);
            vertsMission.setDir(dir);
            vertsMission.setApproxRadius(approxRadius);
            vertsMission.setFlags(flags);
            vertsMission.setApproxHeight(approxHeight);
            vertsMissionList.add(vertsMission);
        }
    }

    private void checkVersion(int version) {
        if (2 != version) {
            throw new RuntimeException("Wrong vertex list BAI file version - found " + version + " expected " + BAI_VERTEX_FILE_VERSION);
        }
    }
}
