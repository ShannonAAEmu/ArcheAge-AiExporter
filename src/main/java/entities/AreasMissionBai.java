package entities;

import models.AreasMission;
import models.PointPos;
import utils.SqliteUtils;
import utils.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AreasMissionBai {

    private final File AREAS_MISSIONS_FILE, AI_GEO_DATA_FOLDER;
    private final List<AreasMission> AREA_MISSIONS_LIST;
    private final String ZONE_ID;
    private final boolean IS_DEBUG;
    private final String GEO_TYPE;
    private File AREAS_MISSIONS_JSON;
    private RandomAccessFile areasMissionRAF;

    public AreasMissionBai(File missionFile, String zoneId, File rootFolder, boolean isDebug, String geoType) throws Exception {
        this.AREAS_MISSIONS_FILE = missionFile;
        this.AREA_MISSIONS_LIST = new ArrayList<>();
        this.AI_GEO_DATA_FOLDER = new File(Files.createDirectories(Paths.get(rootFolder.getPath() + "\\AI GeoData\\" + zoneId)).toString());
        this.ZONE_ID = zoneId;
        this.IS_DEBUG = isDebug;
        this.GEO_TYPE = geoType;
        if (this.IS_DEBUG) {
            this.AREAS_MISSIONS_JSON = new File(Files.createFile(Paths.get(this.AI_GEO_DATA_FOLDER + "\\AreasMission.json")).toString());
        }
    }

    public void readFile() throws Exception {
        initRandomAccessFile();
        Utils.readVersion(areasMissionRAF);
        byte[] bytes;
        while (areasMissionRAF.getFilePointer() < AREAS_MISSIONS_FILE.length() - 20) {
            bytes = Utils.readCustomLength(areasMissionRAF, 16);
            if (!isZeroArray(bytes)) {
                areasMissionRAF.seek(areasMissionRAF.getFilePointer() - 16);
            }
            readData();
        }
        Utils.closeRAF(areasMissionRAF);
        export();
    }

    private void initRandomAccessFile() throws Exception {
        areasMissionRAF = new RandomAccessFile(AREAS_MISSIONS_FILE, "r");
    }

    private void export() throws Exception {
        System.out.println("Total AREA_MISSIONS in " + ZONE_ID + " zone: " + AREA_MISSIONS_LIST.size());
        if (IS_DEBUG) {
            Utils.writeDataToJson(AREAS_MISSIONS_JSON, AREA_MISSIONS_LIST);
        } else {
            if (0 < AREA_MISSIONS_LIST.size())
                SqliteUtils.exportAreasMissionData(AI_GEO_DATA_FOLDER, ZONE_ID, AREA_MISSIONS_LIST, GEO_TYPE);
        }
    }

    private void readData() throws Exception {
        readAreasMissions();
    }

    private void readAreasMissions() throws Exception {
        int areasCount = Utils.readInt(areasMissionRAF);
        for (int i = 0; i < areasCount; i++) {
            AreasMission areaMission = new AreasMission();
            int nameLength = Utils.readInt(areasMissionRAF);
            String name = Utils.readString(areasMissionRAF, nameLength);
            areaMission.setName(name);
            areaMission.setType(areaMission.getName());
            switch (areaMission.getType()) {
                case "ForbiddenArea":
                case "ForbiddenBoundary":
                    readPointsPos(areaMission);
                    break;
                case "AINavigationModifier":
                    readAINavigationModifier(areaMission);
                    break;
                case "AIPath":
                    readAIPath(areaMission);
                    break;
                default:
                    System.out.println("Name: " + areaMission.getName());
                    System.out.println("Pos: " + areasMissionRAF.getFilePointer());
                    System.out.println("Object: " + AREA_MISSIONS_LIST.get(AREA_MISSIONS_LIST.size() - 1));
                    System.out.println("Press any key for exit.");
                    Scanner sc = new Scanner(System.in);
                    sc.nextLine();
                    break;
            }
            isHasAreasMission(areaMission);
        }
    }

    private void readAINavigationModifier(AreasMission areaMission) throws Exception {
        Utils.readCustomLength(areasMissionRAF, 38);
        readPointsPos(areaMission);
    }

    private void readAIPath(AreasMission areaMission) throws Exception {
        readPointsPos(areaMission);
        areaMission.setClosed(false);
        Utils.readCustomLength(areasMissionRAF, 16);
    }

    private void readPointsPos(AreasMission areaMission) throws Exception {
        areaMission.setPoints(Utils.readInt(areasMissionRAF));
        for (int i = 0; i < areaMission.getPoints(); i++) {
            double x = Utils.readDouble(areasMissionRAF);
            double y = Utils.readDouble(areasMissionRAF);
            double z = Utils.readDouble(areasMissionRAF);
            areaMission.getPointsCoords().add(new PointPos(x, y, z));
        }
    }

    private void isHasAreasMission(AreasMission areaMission) {
        for (AreasMission areasMission : AREA_MISSIONS_LIST) {
            if (areasMission.getName().equals(areaMission.getName())) {
                return;
            }
        }
        AREA_MISSIONS_LIST.add(areaMission);
    }

    private boolean isZeroArray(byte[] bytes) {
        for (byte b : bytes) {
            if (0 != b) {
                return false;
            }
        }
        return true;
    }
}
