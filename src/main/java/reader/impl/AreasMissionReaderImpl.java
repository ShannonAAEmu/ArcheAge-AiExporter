package reader.impl;

import entities.Vector;
import entities.export.AreasMissionExport;
import missions.Mission;
import missions.impl.AIShape;
import missions.impl.PolygonArea;
import missions.impl.SpecialArea;
import reader.BaiReader;
import utils.ReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class AreasMissionReaderImpl implements BaiReader {

    private static final int BAI_AREAS_FILE_VERSION = 21;
    private final AreasMissionExport areasMissionExport;
    private final File rawBaiFile;
    private final int zoneId;
    private final List<AIShape> forbiddenAreasList;
    private final List<SpecialArea> navigationModifiersList;
    private final List<AIShape> designerForbiddenAreasList;
    private final List<AIShape> forbiddenBoundariesList;
    private final List<AIShape> extraLinkCostsList;
    private final List<PolygonArea> designerPathsList;
    private ReaderUtil readerUtil;
    private List<Vector> points;

    public AreasMissionReaderImpl(AreasMissionExport areasMissionExport, File rawBaiFile, int zoneId) {
        this.areasMissionExport = areasMissionExport;
        this.rawBaiFile = rawBaiFile;
        this.zoneId = zoneId;
        this.forbiddenAreasList = new ArrayList<>();
        this.navigationModifiersList = new ArrayList<>();
        this.designerForbiddenAreasList = new ArrayList<>();
        this.forbiddenBoundariesList = new ArrayList<>();
        this.extraLinkCostsList = new ArrayList<>();
        this.designerPathsList = new ArrayList<>();

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
        if (BAI_AREAS_FILE_VERSION != version) {
            throw new RuntimeException("Wrong areas BAI file version - found " + version + " expected " + BAI_AREAS_FILE_VERSION);
        }
    }

    @Override
    public void readFromFile() throws IOException {
        long numAreas = readerUtil.readUnsigned();
        AIShape aiShape;
        for (int i = 0; i < numAreas; i++) {
            aiShape = new AIShape(zoneId);
            readForbiddenArea(aiShape, ForbiddenAreasType.FORBIDDEN_AREA);
        }
        numAreas = readerUtil.readUnsigned();
        SpecialArea specialArea;
        for (int i = 0; i < numAreas; i++) {
            specialArea = new SpecialArea(zoneId);
            readNavigationModifiersArea(specialArea);
        }
        numAreas = readerUtil.readUnsigned();
        for (int i = 0; i < numAreas; i++) {
            aiShape = new AIShape(zoneId);
            readDesignerForbiddenAreas(aiShape);
        }
        numAreas = readerUtil.readUnsigned();
        for (int i = 0; i < numAreas; i++) {
            aiShape = new AIShape(zoneId);
            readForbiddenBoundaries(aiShape);
        }
        numAreas = readerUtil.readUnsigned();
        for (int i = 0; i < numAreas; i++) {
            aiShape = new AIShape(zoneId);
            readExtraLinkCostArea(aiShape);
        }
        readerUtil.readLong();                                          // unk data
        numAreas = readerUtil.readUnsigned();
        PolygonArea polygonArea;
        for (int i = 0; i < numAreas; i++) {
            polygonArea = new PolygonArea(zoneId);
            readDesignerPaths(polygonArea);
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
        areasMissionExport.getForbiddenAreasList().add(forbiddenAreasList);
        areasMissionExport.getNavigationModifiersList().add(navigationModifiersList);
        areasMissionExport.getDesignerForbiddenAreasList().add(designerForbiddenAreasList);
        areasMissionExport.getForbiddenBoundariesList().add(forbiddenBoundariesList);
        areasMissionExport.getExtraLinkCostsList().add(extraLinkCostsList);
        areasMissionExport.getDesignerPathsList().add(designerPathsList);
    }

    private void readForbiddenArea(AIShape aiShape, ForbiddenAreasType type) throws IOException {
        readAreaName(aiShape);
        long pointsSize = readerUtil.readUnsigned();
        points = new ArrayList<>();
        for (int i = 0; i < pointsSize; i++) {
            points.add(readCoords());
        }
        aiShape.setPoints(points);
        switch (type) {
            case FORBIDDEN_AREA -> forbiddenAreasList.add(aiShape);
            case DESIGNER_FORBIDDEN_AREA -> designerForbiddenAreasList.add(aiShape);
            case FORBIDDEN_BOUNDARIES -> forbiddenBoundariesList.add(aiShape);
            case EXTRA_LINK -> extraLinkCostsList.add(aiShape);
        }
    }

    private void readNavigationModifiersArea(SpecialArea specialArea) throws IOException {
        readAreaName(specialArea);
        specialArea.setType(SpecialArea.Type.values()[(int) readerUtil.readLong()]);
        specialArea.setWaypointConnections(SpecialArea.WaypointConnections.values()[(int) readerUtil.readLong()]);
        specialArea.setAltered(readerUtil.readByte() != 0);
        specialArea.setHeight(readerUtil.readDouble());
        if (BAI_AREAS_FILE_VERSION <= 16) {
            readerUtil.readDouble();    // junk
        }
        specialArea.setNodeAutoConnectDistance(readerUtil.readDouble());
        specialArea.setMaxZ(readerUtil.readDouble());
        specialArea.setMinZ(readerUtil.readDouble());
        specialArea.setBuildingID(readerUtil.readInt());
        if (BAI_AREAS_FILE_VERSION >= 18) {
            specialArea.setAiLightLevel(SpecialArea.AILightLevel.values()[readerUtil.readByte()]);
        }
        readPoints();
        specialArea.setPoints(points);
        navigationModifiersList.add(specialArea);
    }

    private void readDesignerForbiddenAreas(AIShape aiShape) throws IOException {
        readForbiddenArea(aiShape, ForbiddenAreasType.DESIGNER_FORBIDDEN_AREA);
    }

    private void readForbiddenBoundaries(AIShape aiShape) throws IOException {
        readForbiddenArea(aiShape, ForbiddenAreasType.FORBIDDEN_BOUNDARIES);
    }

    private void readExtraLinkCostArea(AIShape aiShape) throws IOException {
        readForbiddenArea(aiShape, ForbiddenAreasType.EXTRA_LINK);
        extraLinkCostsList.add(aiShape);
    }

    private void readDesignerPaths(PolygonArea polygonArea) throws IOException {
        readAreaName(polygonArea);
        long pointsSize = readerUtil.readUnsigned();
        points = new ArrayList<>();
        for (int i = 0; i < pointsSize; i++) {
            points.add(readCoords());
        }
        polygonArea.setPoints(points);
        polygonArea.setNavigationType(PolygonArea.NavigationType.values()[1 << readerUtil.readInt()]);
        polygonArea.setType(readerUtil.readInt());
        polygonArea.setHeight(readerUtil.readDouble());
        polygonArea.setAiLightLevel(SpecialArea.AILightLevel.values()[readerUtil.readInt()]);
        designerPathsList.add(polygonArea);
    }

    private void readAreaName(Mission mission) throws IOException {
        mission.setName(readerUtil.readString(readerUtil.readInt()));
    }

    private Vector readCoords() throws IOException {
        Vector coordsVector = new Vector();
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }

    private void readPoints() throws IOException {
        points = new ArrayList<>();
        Vector vector;
        long pointsSize = readerUtil.readUnsigned();
        for (int i = 0; i < pointsSize; i++) {
            vector = readCoords();
            points.add(vector);
        }
    }

    public List<AIShape> getForbiddenAreasList() {
        return forbiddenAreasList;
    }

    public List<SpecialArea> getNavigationModifiersList() {
        return navigationModifiersList;
    }

    public List<AIShape> getDesignerForbiddenAreasList() {
        return designerForbiddenAreasList;
    }

    public List<AIShape> getForbiddenBoundariesList() {
        return forbiddenBoundariesList;
    }

    public List<AIShape> getExtraLinkCostsList() {
        return extraLinkCostsList;
    }

    public List<PolygonArea> getDesignerPathsList() {
        return designerPathsList;
    }

    private enum ForbiddenAreasType {
        FORBIDDEN_AREA, DESIGNER_FORBIDDEN_AREA, FORBIDDEN_BOUNDARIES, EXTRA_LINK
    }
}
