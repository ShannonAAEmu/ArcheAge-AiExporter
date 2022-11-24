package reader.impl;

import descriptors.Descriptor;
import descriptors.impl.NodeDescriptor;
import descriptors.impl.links.NodeLinkDescriptor;
import entities.Bbox;
import entities.Vector;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class NetMissionReaderImpl implements BaiReader {
    private static final int BAI_TRI_FILE_VERSION = 55;
    private final File rawBaiFile;
    private final List<Descriptor> nodeDescriptorList;
    private final List<Descriptor> nodeLinkDescriptorList;
    private final int zoneId;
    private ReaderUtil readerUtil;
    private Bbox triangularBBox;

    public NetMissionReaderImpl(File rawBaiFile, int zoneId) {
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.nodeDescriptorList = new ArrayList<>();
        this.nodeLinkDescriptorList = new ArrayList<>();
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
            initTriangularBBox();
            long descriptorsSize = readerUtil.readDescriptorSize();
            if (isValidDescriptor(descriptorsSize)) {
                readDescriptor(descriptorsSize);
            }
            descriptorsSize = readerUtil.readDescriptorSize();
            if (isValidDescriptor(descriptorsSize)) {
                readLinkDescriptors(descriptorsSize);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void checkVersion(int version) {
        if (BAI_TRI_FILE_VERSION != version) {
            throw new RuntimeException("Wrong triangulation BAI file version - found " + version + " expected " + BAI_TRI_FILE_VERSION);
        }
    }

    @Override
    public boolean isValidDescriptor(long size) {
        return 0 < size;
    }

    @Override
    public void readDescriptor(long size) throws IOException {
        NodeDescriptor nodeDescriptor;
        NodeDescriptor.EWaypointNodeType[] eWaypointNodeTypes = NodeDescriptor.EWaypointNodeType.values();
        for (int i = 0; i < size; i++) {
            nodeDescriptor = new NodeDescriptor(triangularBBox);
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
        System.out.println(nodeLinkDescriptorList);
    }

    @Override
    public void save() {

    }

    private void initTriangularBBox() throws IOException {
        triangularBBox = new Bbox();
        triangularBBox.setTriangularBBoxMin(readTriangularBBox("triangularBBoxMin"));
        triangularBBox.setTriangularBBoxMax(readTriangularBBox("triangularBBoxMax"));
    }

    private Vector readTriangularBBox(String name) throws IOException {
        Vector triangularBBox = new Vector(name);
        triangularBBox.setX(readerUtil.readDouble());
        triangularBBox.setY(readerUtil.readDouble());
        triangularBBox.setZ(readerUtil.readDouble());
        return triangularBBox;
    }

    private void readLinkDescriptors(long size) throws IOException {
        NodeLinkDescriptor nodeLinkDescriptor;
        for (int i = 0; i < size; i++) {
            nodeLinkDescriptor = new NodeLinkDescriptor();
            nodeLinkDescriptor.setSourceNode(readerUtil.readInt());
            nodeLinkDescriptor.setTargetNode(readerUtil.readInt());
            nodeLinkDescriptor.setEdgeCenter(readCoords("edgeCenter"));
            nodeLinkDescriptor.setMaxPassRadius(readerUtil.readDouble());
            nodeLinkDescriptor.setExposure(readerUtil.readDouble());
            nodeLinkDescriptor.setLength(readerUtil.readDouble());
            nodeLinkDescriptor.setMaxWaterDepth(readerUtil.readDouble());
            nodeLinkDescriptor.setMinWaterDepth(readerUtil.readDouble());
            nodeLinkDescriptor.setStartIndex(readerUtil.readByte());
            nodeLinkDescriptor.setEndIndex(readerUtil.readByte());
            nodeLinkDescriptor.setIsPureTriangularLink(readerUtil.readByte());
            nodeLinkDescriptor.setSimplePassabilityCheck(readerUtil.readByte());
            nodeLinkDescriptorList.add(nodeLinkDescriptor);
        }
    }

    private Vector readCoords(String name) throws IOException {
        Vector coordsVector = new Vector(name);
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }
}
