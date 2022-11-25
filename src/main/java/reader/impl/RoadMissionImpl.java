package reader.impl;

import descriptors.Descriptor;
import descriptors.impl.RoadDescriptor;
import entities.RoadNode;
import entities.Vector;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class RoadMissionImpl implements BaiReader {
    private static final int BAI_ROADS_FILE_VERSION = 2;
    private final File rawBaiFile;
    private final List<Descriptor> roadDescriptorList;
    private final int zoneId;
    private ReaderUtil readerUtil;

    public RoadMissionImpl(File rawBaiFile, int zoneId) {
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.roadDescriptorList = new ArrayList<>();
    }

    @Override
    public void initReaderUtil() throws FileNotFoundException {
        readerUtil = new ReaderUtil(new RandomAccessFile(rawBaiFile, "r"));
    }

    @Override
    public void readFile() {
        try {
            initReaderUtil();
            int version = readerUtil.readFileVersion();
            checkVersion(version);
            long roads = readerUtil.readDescriptorSize();
            if (isValidDescriptor(roads)) {
                readDescriptor(roads);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void checkVersion(int version) {
        if (BAI_ROADS_FILE_VERSION != version) {
            throw new RuntimeException("Wrong AI roads file version - found " + version + " expected " + BAI_ROADS_FILE_VERSION);
        }
    }

    @Override
    public boolean isValidDescriptor(long count) {
        return 0 < count;
    }

    @Override
    public void readDescriptor(long roads) throws IOException {
        RoadDescriptor roadDescriptor;
        for (int i = 0; i < roads; i++) {
            roadDescriptor = new RoadDescriptor();
            roadDescriptor.setZoneId(zoneId);
            roadDescriptor.setName(readerUtil.readString(readerUtil.readInt()));
            roadDescriptor.setRoadNodeList(readRoadNode());
            roadDescriptorList.add(roadDescriptor);
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
        System.out.println(roadDescriptorList);
    }

    @Override
    public void save() {
    }

    private List<RoadNode> readRoadNode() throws IOException {
        List<RoadNode> roadNodeList = new ArrayList<>();
        long points = readerUtil.readByte();
        RoadNode road;
        for (int i = 0; i < points; i++) {
            road = new RoadNode();
            road.setNodeIndex(readerUtil.readInt());
            road.setPos(readCoords());
            road.setWidth(readerUtil.readDouble());
            road.setOffset(readerUtil.readDouble());
            roadNodeList.add(road);
        }
        return roadNodeList;
    }

    private Vector readCoords() throws IOException {
        Vector coordsVector = new Vector("pos");
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }
}
