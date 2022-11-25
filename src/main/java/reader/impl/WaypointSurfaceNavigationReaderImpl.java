package reader.impl;

import descriptors.impl.LinkRecordDescriptor;
import entities.LinkRecord;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class WaypointSurfaceNavigationReaderImpl implements BaiReader {
    private static final int BAI_WAYPOINT_SURFACE_FILE_VERSION = 1;
    private final File rawBaiFile;
    private final List<LinkRecordDescriptor> linkRecordDescriptorList;
    private final int zoneId;
    private ReaderUtil readerUtil;

    public WaypointSurfaceNavigationReaderImpl(File rawBaiFile, int zoneId) {
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.linkRecordDescriptorList = new ArrayList<>();
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
            long descriptorsSize = readerUtil.readDescriptorSize();
            if (isValidDescriptor(descriptorsSize)) {
                readDescriptor(descriptorsSize);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void checkVersion(int version) {
        if (BAI_WAYPOINT_SURFACE_FILE_VERSION != version) {
            throw new RuntimeException("Wrong AI 3D surface waypoint file version - found " + version + " expected " + BAI_WAYPOINT_SURFACE_FILE_VERSION);
        }
    }

    @Override
    public boolean isValidDescriptor(long count) {
        return 0 < count;
    }

    @Override
    public void readDescriptor(long count) throws IOException {
        LinkRecord linkRecord;
        LinkRecordDescriptor linkRecordDescriptor = new LinkRecordDescriptor();
        linkRecordDescriptor.setZoneId(zoneId);
        List<LinkRecord> linkedVolumeRecords = new ArrayList<>();
        List<LinkRecord> linkedFlightRecords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            long records = readerUtil.readDescriptorSize();
            if (isValidDescriptor(records)) {
                linkRecord = initLinkRecord();
                linkedVolumeRecords.add(linkRecord);
                linkRecordDescriptor.setLinkedVolumeRecords(linkedVolumeRecords);
            }
            records = readerUtil.readDescriptorSize();
            if (isValidDescriptor(records)) {
                linkRecord = initLinkRecord();
                linkedFlightRecords.add(linkRecord);
                linkRecordDescriptor.setLinkedFlightRecords(linkedFlightRecords);
            }
            linkRecordDescriptorList.add(linkRecordDescriptor);
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
        System.out.println(linkRecordDescriptorList);
    }

    @Override
    public void save() {

    }

    private LinkRecord initLinkRecord() throws IOException {
        LinkRecord linkRecord = new LinkRecord();
        linkRecord.setNodeIndex(readerUtil.readInt());
        linkRecord.setRadiusOut(readerUtil.readDouble());
        linkRecord.setRadiusIn(readerUtil.readDouble());
        return linkRecord;
    }
}
