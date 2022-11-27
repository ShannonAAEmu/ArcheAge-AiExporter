package reader.impl;

import entities.Road;
import entities.RoadNode;
import entities.Vector;
import entities.export.RoadMissionExport;
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
    private final RoadMissionExport roadMissionExport;
    private final File rawBaiFile;
    private final int zoneId;

    private final List<Road> roadList;
    private ReaderUtil readerUtil;

    public RoadMissionImpl(RoadMissionExport roadMissionExport, File rawBaiFile, int zoneId) {
        this.roadMissionExport = roadMissionExport;
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.roadList = new ArrayList<>();
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
            readFromFile();
        } catch (Exception e) {
            System.out.println("Pos: " + readerUtil.getPos());
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    @Override
    public void checkVersion(int version) {
        if (BAI_ROADS_FILE_VERSION != version) {
            throw new RuntimeException("Wrong road BAI file version - found " + version + " expected " + BAI_ROADS_FILE_VERSION);
        }
    }

    @Override
    public void readFromFile() throws IOException {
        long roads = readerUtil.readUnsigned();
        Road road;
        for (int i = 0; i < roads; i++) {
            road = readRoad();
            roadList.add(road);
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
    public void prepareExport() {
        roadMissionExport.getRoadList().add(roadList);
    }

    private Road readRoad() throws IOException {
        Road road = new Road();
        List<RoadNode> roadNodeList = new ArrayList<>();
        RoadNode roadNode;
        road.setZoneId(zoneId);
        road.setName(readerUtil.readString(readerUtil.readInt()));
        long points = readerUtil.readUnsigned();
        for (int i = 0; i < points; i++) {
            roadNode = new RoadNode();
            roadNode.setPos(readCoords());
            roadNode.setWidth(readerUtil.readDouble());
            roadNode.setOffset(readerUtil.readDouble());
            roadNodeList.add(roadNode);
        }
        road.setRoadNodeList(roadNodeList);
        return road;
    }

    private Vector readCoords() throws IOException {
        Vector coordsVector = new Vector();
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }
}
