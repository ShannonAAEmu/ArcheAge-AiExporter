package reader.impl;

import entities.*;
import entities.export.NetMissionExport;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class NetMissionReaderImpl implements BaiReader {

    private static final int BAI_TRIANGULATION_FILE_VERSION = 55;
    private final NetMissionExport netMissionExport;
    private final File rawBaiFile;
    private final List<NodeDescriptor> nodeDescriptorList;
    private final List<LinkDescriptor> linkDescriptorList;
    private final Navigation navigation;
    private ReaderUtil readerUtil;

    private BBox bBox;

    public NetMissionReaderImpl(NetMissionExport netMissionExport, File rawBaiFile, int zoneId) {
        this.netMissionExport = netMissionExport;
        this.rawBaiFile = rawBaiFile;
        this.nodeDescriptorList = new ArrayList<>();
        this.linkDescriptorList = new ArrayList<>();
        this.navigation = new Navigation(zoneId);
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
        if (BAI_TRIANGULATION_FILE_VERSION != version) {
            throw new RuntimeException("Wrong triangulation BAI file version " + version + " expected " + BAI_TRIANGULATION_FILE_VERSION);
        }
    }

    @Override
    public void readFromFile() throws IOException {
        readBBox();
        readNodeDescriptors();
        readLinkDescriptors();
        setNavigation();
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
        netMissionExport.getNavigations().add(navigation);
    }

    private void readBBox() throws IOException {
        bBox = new BBox();
        bBox.setMin(readCoords());
        bBox.setMax(readCoords());
    }

    private void readNodeDescriptors() throws IOException {
        int count = readerUtil.readInt();
        NodeDescriptor nodeDescriptor;
        for (int i = 0; i < count; i++) {
            nodeDescriptor = new NodeDescriptor();
            nodeDescriptor.setId(readerUtil.readInt());
            nodeDescriptor.setDir(readCoords());
            nodeDescriptor.setUp(readCoords());
            nodeDescriptor.setPos(readCoords());
            nodeDescriptor.setIndex(readerUtil.readInt());
            for (int j = 0; j < nodeDescriptor.getObstacle().length; j++) {
                nodeDescriptor.getObstacle()[j] = readerUtil.readInt();
            }
            nodeDescriptor.setType(readerUtil.readByte());
            nodeDescriptor.setUnk1(readerUtil.readByte());
            nodeDescriptor.setUnk2(readerUtil.readByte());
            nodeDescriptor.setUnk3(readerUtil.readByte());
            nodeDescriptorList.add(nodeDescriptor);
        }
    }

    private void readLinkDescriptors() throws IOException {
        int count = readerUtil.readInt();
        LinkDescriptor linkDescriptor;
        for (int i = 0; i < count; i++) {
            linkDescriptor = new LinkDescriptor();
            linkDescriptor.setSourceNode(readerUtil.readUnsigned());
            linkDescriptor.setTargetNode(readerUtil.readUnsigned());
            linkDescriptor.setEdgeCenter(readCoords());
            linkDescriptor.setMaxPassRadius(readerUtil.readDouble());
            linkDescriptor.setExposure(readerUtil.readDouble());
            linkDescriptor.setLength(readerUtil.readDouble());
            linkDescriptor.setMaxWaterDepth(readerUtil.readDouble());
            linkDescriptor.setMinWaterDepth(readerUtil.readDouble());
            linkDescriptor.setStartIndex(readerUtil.readByte());
            linkDescriptor.setEndIndex(readerUtil.readByte());
            linkDescriptor.setPureTriangularLink(1 == readerUtil.readByte());
            linkDescriptor.setSimplePassabilityCheck(1 == readerUtil.readByte());
            linkDescriptorList.add(linkDescriptor);
        }
    }

    private Vector readCoords() throws IOException {
        Vector coordsVector = new Vector();
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }

    private void setNavigation() {
        navigation.setbBox(bBox);
        navigation.setDescriptorList(nodeDescriptorList);
        navigation.setLinkDescriptorList(linkDescriptorList);
    }
}
