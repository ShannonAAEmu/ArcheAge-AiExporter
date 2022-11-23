package reader.impl;

import descriptors.Descriptor;
import descriptors.impl.ObstacleDataDescriptor;
import entities.Vector;
import reader.BaiReader;
import reader.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class VertexMissionReaderImpl implements BaiReader {
    private static final int BAI_VERTEX_FILE_VERSION = 2;
    private final File rawBaiFile;
    private final List<Descriptor> obstacleDataDescList;
    private final int zoneId;
    private ReaderUtil readerUtil;

    public VertexMissionReaderImpl(File rawBaiFile, int zoneId) {
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.obstacleDataDescList = new ArrayList<>();
    }

    @Override
    public void read() {
        try {
            initReaderUtil();
            int version = readFileVersion();
            checkVersion(version);
            int descriptorsSize = readDescriptorsSize();
            if (isValidDescriptors(descriptorsSize)) {
                readDescriptors(descriptorsSize);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int readFileVersion() throws IOException {
        return readerUtil.readInt();
    }

    @Override
    public void checkVersion(int version) {
        if (BAI_VERTEX_FILE_VERSION != version) {
            throw new RuntimeException("Wrong vertex list BAI file version - found " + version + " expected " + BAI_VERTEX_FILE_VERSION);
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
        System.out.println(obstacleDataDescList);
    }

    @Override
    public void save() {

    }

    @Override
    public void readLinkDescriptors(int size) {
    }

    private void initReaderUtil() throws FileNotFoundException {
        readerUtil = new ReaderUtil(new RandomAccessFile(rawBaiFile, "r"));
    }

    private int readDescriptorsSize() throws IOException {
        return readerUtil.readInt();
    }

    private void readDescriptors(int size) throws IOException {
        ObstacleDataDescriptor obstacleDataDescriptor;
        for (int i = 0; i < size; i++) {
            obstacleDataDescriptor = new ObstacleDataDescriptor();
            obstacleDataDescriptor.setZoneId(zoneId);
            obstacleDataDescriptor.setPos(readCoords("pos"));
            obstacleDataDescriptor.setDir(readCoords("dir"));
            obstacleDataDescriptor.setApproxRadius(readerUtil.readDouble());
            obstacleDataDescriptor.setFlags(readerUtil.readByte());
            obstacleDataDescriptor.setApproxHeight(readerUtil.readByte());
            readerUtil.readShortSeparator();
            obstacleDataDescList.add(obstacleDataDescriptor);
        }
    }

    private Vector readCoords(String name) throws IOException {
        Vector coordsVector = new Vector(name);
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }

    private boolean isValidDescriptors(int size) {
        return 0 < size;
    }
}
