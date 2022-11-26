package reader.impl;

import area.Area;
import area.impl.AIShape;
import area.impl.ExtraLinkCostShape;
import area.impl.PolygonArea;
import area.impl.SpecialArea;
import entities.neww.AABB;
import entities.neww.Vector;
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
    private final File rawBaiFile;
    private final int zoneId;
    private final List<AIShape> forbiddenAreasList;
    private final List<SpecialArea> navigationModifiersList;
    private final List<AIShape> designerForbiddenAreasList;
    private final List<AIShape> forbiddenBoundariesList;
    private final List<ExtraLinkCostShape> extraLinkCostsList;
    private final List<PolygonArea> designerPathsList;
    private ReaderUtil readerUtil;
    private List<Vector> points;

    public AreasMissionReaderImpl(File rawBaiFile, int zoneId) {
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
        ExtraLinkCostShape extraLinkCostShape;
        for (int i = 0; i < numAreas; i++) {
            extraLinkCostShape = new ExtraLinkCostShape(zoneId);
            readExtraLinkCostArea(extraLinkCostShape);
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
    public void print() {
        System.out.println(forbiddenAreasList);
        System.out.println(navigationModifiersList);
        System.out.println(designerForbiddenAreasList);
        System.out.println(forbiddenBoundariesList);
        System.out.println(extraLinkCostsList);
        System.out.println(designerPathsList);
    }

    private void readForbiddenArea(AIShape aiShape, ForbiddenAreasType type) throws IOException {
        readAreaName(aiShape);
        long pointsSize = readerUtil.readUnsigned();
        points = new ArrayList<>();
        for (int i = 0; i < pointsSize; i++) {
            points.add(readCoords(aiShape.getName()));
        }
        aiShape.setPoints(points);
        switch (type) {
            case FORBIDDEN_AREA -> forbiddenAreasList.add(aiShape);
            case DESIGNER_FORBIDDEN_AREA -> designerForbiddenAreasList.add(aiShape);
            case FORBIDDEN_BOUNDARIES -> forbiddenBoundariesList.add(aiShape);
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
        readPoints(specialArea);
        specialArea.setPoints(points);
        navigationModifiersList.add(specialArea);
    }

    private void readDesignerForbiddenAreas(AIShape aiShape) throws IOException {
        readForbiddenArea(aiShape, ForbiddenAreasType.DESIGNER_FORBIDDEN_AREA);
    }

    private void readForbiddenBoundaries(AIShape aiShape) throws IOException {
        readForbiddenArea(aiShape, ForbiddenAreasType.FORBIDDEN_BOUNDARIES);
    }

    private void readExtraLinkCostArea(ExtraLinkCostShape extraLinkCostShape) throws IOException {
        readAreaName(extraLinkCostShape);
        extraLinkCostShape.setCostFactor(readerUtil.readDouble());
        AABB aabb = new AABB();
        aabb.setMin(readCoords(extraLinkCostShape.getName()));
        aabb.setMax(readCoords(extraLinkCostShape.getName()));
        extraLinkCostShape.setAabb(aabb);
        readPoints(extraLinkCostShape);
        extraLinkCostShape.setPoints(points);
        extraLinkCostsList.add(extraLinkCostShape);
    }

    private void readDesignerPaths(PolygonArea polygonArea) throws IOException {
        readAreaName(polygonArea);
        long pointsSize = readerUtil.readUnsigned();
        points = new ArrayList<>();
        for (int i = 0; i < pointsSize; i++) {
            points.add(readCoords(polygonArea.getName()));
        }
        polygonArea.setPoints(points);
        polygonArea.setNavigationType(PolygonArea.NavigationType.values()[1 << readerUtil.readInt()]);
        polygonArea.setType(readerUtil.readInt());
        polygonArea.setHeight(readerUtil.readDouble());
        polygonArea.setAiLightLevel(SpecialArea.AILightLevel.values()[readerUtil.readInt()]);
        designerPathsList.add(polygonArea);
    }

    private void readAreaName(Area area) throws IOException {
        area.setName(readerUtil.readString(readerUtil.readInt()));
    }

    private Vector readCoords(String name) throws IOException {
        Vector coordsVector = new Vector(name);
        coordsVector.setX(readerUtil.readDouble());
        coordsVector.setY(readerUtil.readDouble());
        coordsVector.setZ(readerUtil.readDouble());
        return coordsVector;
    }

    private void readPoints(Area area) throws IOException {
        points = new ArrayList<>();
        Vector vector;
        long pointsSize = readerUtil.readUnsigned();
        for (int i = 0; i < pointsSize; i++) {
            vector = readCoords(area.getName());
            points.add(vector);
        }
    }

    private enum ForbiddenAreasType {
        FORBIDDEN_AREA, DESIGNER_FORBIDDEN_AREA, FORBIDDEN_BOUNDARIES
    }
}
