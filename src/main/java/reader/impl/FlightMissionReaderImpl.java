package reader.impl;

import descriptors.Descriptor;
import descriptors.impl.FlightDescriptor;
import descriptors.impl.links.FlightLinkDescriptor;
import entities.FlightNavRegion;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FlightMissionReaderImpl implements BaiReader {
    private static final int BAI_FLIGHT_NAV_FILE_VERSION = 9;
    private final File rawBaiFile;
    private final List<Descriptor> flightDescriptorList;
    private final List<Descriptor> flightLinkDescriptorList;
    private final int zoneId;
    private ReaderUtil readerUtil;
    private FlightNavRegion flightNavRegion;

    public FlightMissionReaderImpl(File rawBaiFile, int zoneId) {
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.flightDescriptorList = new ArrayList<>();
        this.flightLinkDescriptorList = new ArrayList<>();
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
            initFlightNavRegion();
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
        if (BAI_FLIGHT_NAV_FILE_VERSION != version) {
            throw new RuntimeException("Wrong flight BAI file version - found " + version + " expected " + BAI_FLIGHT_NAV_FILE_VERSION);
        }
    }

    @Override
    public boolean isValidDescriptor(long count) {
        return 0 < count;
    }

    @Override
    public void readDescriptor(long count) throws IOException {
        FlightDescriptor flightDescriptor;
        for (int i = 0; i < count; i++) {
            flightDescriptor = new FlightDescriptor();
            flightDescriptor.setZoneId(zoneId);
            flightDescriptor.setFlightNavRegion(flightNavRegion);
            flightDescriptor.setX(readerUtil.readDouble());
            flightDescriptor.setY(readerUtil.readDouble());
            flightDescriptor.setMinZ(readerUtil.readDouble());
            flightDescriptor.setMaxZ(readerUtil.readDouble());
            flightDescriptor.setMaxRadius(readerUtil.readDouble());
            flightDescriptor.setClassification(readerUtil.readInt());
            flightDescriptor.setChildIdx(readerUtil.readInt());
            flightDescriptor.setNextIdx(readerUtil.readInt());
            flightDescriptorList.add(flightDescriptor);
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
        System.out.println(flightDescriptorList);
        System.out.println(flightLinkDescriptorList);
    }

    @Override
    public void save() {
    }

    private void initFlightNavRegion() throws IOException {
        flightNavRegion = new FlightNavRegion();
        flightNavRegion.setHeightFieldOriginX(readerUtil.readInt());
        flightNavRegion.setHeightFieldOriginY(readerUtil.readInt());
        flightNavRegion.setHeightFieldDimX(readerUtil.readInt());
        flightNavRegion.setHeightFieldDimY(readerUtil.readInt());
        flightNavRegion.setChildSubDiv(readerUtil.readInt());
        flightNavRegion.setTerrainDownSample(readerUtil.readInt());
    }

    public void readLinkDescriptors(long size) throws IOException {
        FlightLinkDescriptor flightLinkDescriptor;
        for (int i = 0; i < size; i++) {
            flightLinkDescriptor = new FlightLinkDescriptor();
            flightLinkDescriptor.setStartIdx(readerUtil.readInt());
            flightLinkDescriptor.setEndIdx(readerUtil.readInt());
            flightLinkDescriptorList.add(flightLinkDescriptor);
        }
    }
}
