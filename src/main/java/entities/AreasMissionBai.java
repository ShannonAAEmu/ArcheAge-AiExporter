package entities;

import models.AreasMission;
import models.PointPos;
import utils.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class AreasMissionBai {

    private final File AREAS_MISSIONS, AREAS_MISSIONS_JSON;
    private final List<AreasMission> AREA_MISSIONS;
    private int areasCount;
    private RandomAccessFile areasMissionRAF;

    public AreasMissionBai(File missionFile, String path) throws Exception {
        this.AREAS_MISSIONS = missionFile;
        this.AREA_MISSIONS = new ArrayList<>();
        new File(path + "\\Export AI").mkdir();
        this.AREAS_MISSIONS_JSON = new File(path + "\\Export AI\\AreasMission.json");
        this.AREAS_MISSIONS_JSON.createNewFile();
    }

    public void readFile() throws Exception {
        initRandomAccessFile();
        Utils.readVersion(areasMissionRAF);
        byte[] bytes;
        while (areasMissionRAF.getFilePointer() < AREAS_MISSIONS.length() - 20) {
            bytes = Utils.readCustomLength(areasMissionRAF, 16);
            if (!isZeroArray(bytes)) {
                areasMissionRAF.seek(areasMissionRAF.getFilePointer() - 16);
            }
            readData();
        }
        assert AREA_MISSIONS != null;
        Utils.writeDataToJson(AREAS_MISSIONS_JSON, AREA_MISSIONS);
        Utils.closeRAF(areasMissionRAF);
    }

    private void initRandomAccessFile() throws Exception {
        areasMissionRAF = new RandomAccessFile(AREAS_MISSIONS, "r");
    }

    private void readData() throws Exception {
        readAreasCount();
        readAreasMissions();
    }

    private void readAreasCount() throws Exception {
        areasCount = Utils.readInt(areasMissionRAF);
    }

    private void readAreasMissions() throws Exception {
        for (int i = 0; i < areasCount; i++) {
            AreasMission areaMission = new AreasMission();
            int nameLength = Utils.readInt(areasMissionRAF);
            String name = Utils.readString(areasMissionRAF, nameLength);
            areaMission.setName(name);
            areaMission.setType(areaMission.getName());
            switch (areaMission.getType()) {
                case "ForbiddenArea":
                    readPointsPos(areaMission);
                    break;
                case "AINavigationModifier":
                    readAINavigationModifier(areaMission);
                    break;
                case "AIPath":
                    readAIPath(areaMission);
                    break;
                default:
                    System.out.println(areaMission.getName());
                    System.out.println(areasMissionRAF.getFilePointer());
                    System.out.println(AREA_MISSIONS.get(AREA_MISSIONS.size() - 1));
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
        int pointsCount = Utils.readInt(areasMissionRAF);
        areaMission.setPoints(pointsCount);
        for (int i = 0; i < areaMission.getPoints(); i++) {
            double x = Utils.readDouble(areasMissionRAF);
            double y = Utils.readDouble(areasMissionRAF);
            double z = Utils.readDouble(areasMissionRAF);
            areaMission.getPointsCoords().add(new PointPos(x, y, z));
        }
    }

    private void isHasAreasMission(AreasMission areaMission) {
        for (AreasMission areasMission : AREA_MISSIONS) {
            if (areasMission.getName().equals(areaMission.getName())) {
                return;
            }
        }
        AREA_MISSIONS.add(areaMission);
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
