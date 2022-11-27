package reader.impl;

import entities.FlightLinkDescriptor;
import entities.Span;
import entities.export.FlightMissionExport;
import missions.impl.FlightNavRegion;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FlightMissionReaderImpl implements BaiReader {

    private static final int BAI_FLIGHT_NAV_FILE_VERSION_READ = 9;
    private final FlightMissionExport flightMissionExport;
    private final File rawBaiFile;
    private final int zoneId;
    private ReaderUtil readerUtil;
    private FlightNavRegion flightNavRegion;

    public FlightMissionReaderImpl(FlightMissionExport flightMissionExport, File rawBaiFile, int zoneId) {
        this.flightMissionExport = flightMissionExport;
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
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
        if (BAI_FLIGHT_NAV_FILE_VERSION_READ != version) {
            throw new RuntimeException("Wrong flight BAI file version - found " + version + " expected " + BAI_FLIGHT_NAV_FILE_VERSION_READ);
        }
    }

    @Override
    public void readFromFile() throws IOException {
        flightNavRegion = new FlightNavRegion(zoneId);
        flightNavRegion.setName("flight_nav_region");
        flightNavRegion.setHeightFieldOriginX(readerUtil.readInt());
        flightNavRegion.setHeightFieldOriginY(readerUtil.readInt());
        flightNavRegion.setHeightFieldDimX(readerUtil.readInt());
        flightNavRegion.setHeightFieldDimY(readerUtil.readInt());
        flightNavRegion.setChildSubDiv(readerUtil.readInt());
        flightNavRegion.setTerrainDownSample(readerUtil.readInt());
        long spanCount = readerUtil.readUnsigned();
        readSpans(flightNavRegion, spanCount);
        long linkCount = readerUtil.readUnsigned();
        readLinksDescriptors(flightNavRegion, linkCount);
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
        flightMissionExport.getFlightNavRegion().add(flightNavRegion);
    }

    private void readSpans(FlightNavRegion flightNavRegion, long spanCount) throws IOException {
        List<Span> spanList = new ArrayList<>();
        Span span;
        for (int i = 0; i < spanCount; i++) {
            span = new Span();
            readSpan(span);
            spanList.add(span);
        }
        flightNavRegion.setSpanList(spanList);
    }

    private void readSpan(Span span) throws IOException {
        span.setX(readerUtil.readDouble());
        span.setY(readerUtil.readDouble());
        span.setMinZ(readerUtil.readDouble());
        span.setMaxZ(readerUtil.readDouble());
        span.setMaxRadius(readerUtil.readDouble());
        span.setClassification(readerUtil.readInt());
        span.setChildIdx(readerUtil.readInt());
        span.setNextIdx(readerUtil.readInt());
    }

    private void readLinksDescriptors(FlightNavRegion flightNavRegion, long linkCount) throws IOException {
        List<FlightLinkDescriptor> flightLinkDescriptorList = new ArrayList<>();
        FlightLinkDescriptor flightLinkDescriptor;
        for (int i = 0; i < linkCount; i++) {
            flightLinkDescriptor = new FlightLinkDescriptor();
            flightLinkDescriptor.setIndexFirst(readerUtil.readInt());
            flightLinkDescriptor.setIndexSecond(readerUtil.readInt());
            flightLinkDescriptorList.add(flightLinkDescriptor);
        }
        flightNavRegion.setFlightLinkDescriptorList(flightLinkDescriptorList);
    }
}
