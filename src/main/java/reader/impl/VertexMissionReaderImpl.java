package reader.impl;

import entities.ObstacleDataDescriptor;
import entities.Vector;
import entities.export.VertexMissionExport;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class VertexMissionReaderImpl implements BaiReader {

    private static final int BAI_VERTEX_FILE_VERSION = 2;
    private final VertexMissionExport vertexMissionExport;
    private final File rawBaiFile;
    private final int zoneId;
    private final List<ObstacleDataDescriptor> obstacleDataDescriptorList;
    private ReaderUtil readerUtil;

    public VertexMissionReaderImpl(VertexMissionExport vertexMissionExport, File rawBaiFile, int zoneId) {
        this.vertexMissionExport = vertexMissionExport;
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.obstacleDataDescriptorList = new ArrayList<>();
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
        if (BAI_VERTEX_FILE_VERSION != version) {
            throw new RuntimeException("Wrong vertex list BAI file version " + version + " expected " + BAI_VERTEX_FILE_VERSION);
        }
    }

    @Override
    public void readFromFile() throws IOException {
        readNumberOfDescriptors();
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
        vertexMissionExport.getObstacleDataDescriptorList().add(obstacleDataDescriptorList);
    }

    private void readNumberOfDescriptors() throws IOException {
        int count = readerUtil.readInt();
        ObstacleDataDescriptor obstacleDataDescriptor;
        for (int i = 0; i < count; i++) {
            obstacleDataDescriptor = new ObstacleDataDescriptor(zoneId);
            obstacleDataDescriptor.setPos(readCoords());
            obstacleDataDescriptor.setDir(readCoords());
            obstacleDataDescriptor.setApproxRadius(readerUtil.readDouble());
            obstacleDataDescriptor.setFlags(readerUtil.readByte());
            obstacleDataDescriptor.setApproxHeight(readerUtil.readByte());
            obstacleDataDescriptor.setUnk1(readerUtil.readByte());
            obstacleDataDescriptor.setUnk2(readerUtil.readByte());
            obstacleDataDescriptorList.add(obstacleDataDescriptor);
        }
    }

    private Vector readCoords() throws IOException {
        Vector coordsVector = new Vector();
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }
}
