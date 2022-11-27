package reader.impl;

import entities.LinkRecord;
import entities.WaypointSurfaceNavigation;
import entities.export.WaypointSurfaceNavigationExport;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class WaypointSurfaceNavigationReaderImpl implements BaiReader {

    private static final int BAI_WAY_POINT_3D_SURFACE_FILE_VERSION = 1;
    private final WaypointSurfaceNavigationExport waypointSurfaceNavigationExport;
    private final File rawBaiFile;
    private final int zoneId;
    private final List<LinkRecord> linkedVolumeRecords;
    private final List<LinkRecord> linkedFlightRecords;
    private final List<WaypointSurfaceNavigation> waypointSurfaceNavigationList;
    private ReaderUtil readerUtil;

    public WaypointSurfaceNavigationReaderImpl(WaypointSurfaceNavigationExport waypointSurfaceNavigationExport, File rawBaiFile, int zoneId) {
        this.waypointSurfaceNavigationExport = waypointSurfaceNavigationExport;
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.linkedVolumeRecords = new ArrayList<>();
        this.linkedFlightRecords = new ArrayList<>();
        this.waypointSurfaceNavigationList = new ArrayList<>();
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
        if (BAI_WAY_POINT_3D_SURFACE_FILE_VERSION != version) {
            throw new RuntimeException("Wrong AI 3D surface waypoint file version " + version + " expected " + BAI_WAY_POINT_3D_SURFACE_FILE_VERSION);
        }
    }

    @Override
    public void readFromFile() throws IOException {
        long nodes = readerUtil.readUnsigned();
        WaypointSurfaceNavigation waypointSurfaceNavigation;
        for (int i = 0; i < nodes; i++) {
            readLinkedVolumeRecords(RecordsType.LINKED_VOLUME);
            readLinkedVolumeRecords(RecordsType.FLIGHT);
            waypointSurfaceNavigation = new WaypointSurfaceNavigation(zoneId);
            waypointSurfaceNavigation.setLinkedVolumeRecords(linkedVolumeRecords);
            waypointSurfaceNavigation.setLinkedFlightRecords(linkedFlightRecords);
            waypointSurfaceNavigationList.add(waypointSurfaceNavigation);
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
        waypointSurfaceNavigationExport.getWaypointSurfaceNavigationList().add(waypointSurfaceNavigationList);
    }

    private void readLinkedVolumeRecords(RecordsType recordsType) throws IOException {
        long records = readerUtil.readUnsigned();
        LinkRecord linkRecord;
        for (int i = 0; i < records; i++) {
            linkRecord = new LinkRecord();
            linkRecord.setNodeIndex(readerUtil.readInt());
            linkRecord.setRadiusOut(readerUtil.readDouble());
            linkRecord.setRadiusIn(readerUtil.readDouble());
            switch (recordsType) {
                case LINKED_VOLUME -> linkedVolumeRecords.add(linkRecord);
                case FLIGHT -> linkedFlightRecords.add(linkRecord);
            }
        }
    }

    public enum RecordsType {
        LINKED_VOLUME, FLIGHT
    }
}
