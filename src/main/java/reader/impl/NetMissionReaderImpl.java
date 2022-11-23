package reader.impl;

import entities.BaiFile;
import entities.impl.LinkDescriptor;
import entities.impl.NodeDescriptor;
import models.Vector;
import reader.BaiReader;
import reader.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class NetMissionReaderImpl implements BaiReader {

    private static final int BAI_TRI_FILE_VERSION = 55;
    private final File rawBaiFile;
    private final List<BaiFile> nodeDescriptorList;
    private final List<BaiFile> linkDescriptorList;
    private final int zoneId;
    private ReaderUtil readerUtil;
    private Vector triangularBBoxMin;
    private Vector triangularBBoxMax;

    public NetMissionReaderImpl(File rawBaiFile, int zoneId) {
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.nodeDescriptorList = new ArrayList<>();
        this.linkDescriptorList = new ArrayList<>();
    }

    @Override
    public void read() {
        try {
            initReaderUtil();
            int version = readFileVersion();
            checkVersion(version);
            setBBox();
            int descriptorsSize = readDescriptorsSize();
            if (isValidDescriptor(descriptorsSize)) {
                readNodeDescriptors(descriptorsSize);
            }
            descriptorsSize = readDescriptorsSize();
            if (isValidDescriptor(descriptorsSize)) {
                readLinkDescriptors(descriptorsSize);
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
        if (BAI_TRI_FILE_VERSION != version) {
            throw new RuntimeException("Wrong vertex list BAI file version - found " + version + " expected " + BAI_TRI_FILE_VERSION);
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
        System.out.println(nodeDescriptorList);
        System.out.println(linkDescriptorList);
    }

    @Override
    public void save() {

    }

    private void initReaderUtil() throws FileNotFoundException {
        readerUtil = new ReaderUtil(new RandomAccessFile(rawBaiFile, "r"));
    }

    private void setBBox() throws IOException {
        triangularBBoxMin = readTriangularBBox("triangularBBoxMin");
        triangularBBoxMax = readTriangularBBox("triangularBBoxMax");
    }

    private Vector readTriangularBBox(String name) throws IOException {
        Vector triangularBBox = new Vector(name);
        triangularBBox.setX(readerUtil.readDouble());
        triangularBBox.setY(readerUtil.readDouble());
        triangularBBox.setZ(readerUtil.readDouble());
        return triangularBBox;
    }

    private int readDescriptorsSize() throws IOException {
        return readerUtil.readInt();
    }

    private void readNodeDescriptors(int size) throws IOException {
        NodeDescriptor nodeDescriptor;
        NodeDescriptor.EWaypointNodeType[] eWaypointNodeTypes = NodeDescriptor.EWaypointNodeType.values();
        for (int i = 0; i < size; i++) {
            nodeDescriptor = new NodeDescriptor(triangularBBoxMin, triangularBBoxMax);
            nodeDescriptor.setZoneId(zoneId);
            nodeDescriptor.setId(readerUtil.readInt());
            nodeDescriptor.setDir(readCoords("dir"));
            nodeDescriptor.setUp(readCoords("up"));
            nodeDescriptor.setPos(readCoords("pos"));
            nodeDescriptor.setIndex(readerUtil.readInt());
            nodeDescriptor.setObstacle1(readerUtil.readInt());
            nodeDescriptor.setObstacle2(readerUtil.readInt());
            nodeDescriptor.setObstacle3(readerUtil.readInt());
            nodeDescriptor.setType(eWaypointNodeTypes[readerUtil.readByte()]);
            nodeDescriptor.setForbidden(readerUtil.readByte());
            nodeDescriptor.setUnk1(readerUtil.readByte());
            nodeDescriptor.setUnk2(readerUtil.readByte());
            nodeDescriptorList.add(nodeDescriptor);
        }
    }

    private void readLinkDescriptors(int size) throws IOException {
        LinkDescriptor linkDescriptor;
        for (int i = 0; i < size; i++) {
            linkDescriptor = new LinkDescriptor();
            linkDescriptor.setSourceNode(readerUtil.readInt());
            linkDescriptor.setTargetNode(readerUtil.readInt());
            linkDescriptor.setEdgeCenter(readCoords("edgeCenter"));
            linkDescriptor.setMaxPassRadius(readerUtil.readDouble());
            linkDescriptor.setExposure(readerUtil.readDouble());
            linkDescriptor.setLength(readerUtil.readDouble());
            linkDescriptor.setMaxWaterDepth(readerUtil.readDouble());
            linkDescriptor.setMinWaterDepth(readerUtil.readDouble());
            linkDescriptor.setStartIndex(readerUtil.readByte());
            linkDescriptor.setEndIndex(readerUtil.readByte());
            linkDescriptor.setIsPureTriangularLink(readerUtil.readByte());
            linkDescriptor.setSimplePassabilityCheck(readerUtil.readByte());
            linkDescriptorList.add(linkDescriptor);
        }
    }

    private Vector readCoords(String name) throws IOException {
        Vector coordsVector = new Vector(name);
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }

    private boolean isValidDescriptor(int size) {
        return 0 < size;
    }
}
