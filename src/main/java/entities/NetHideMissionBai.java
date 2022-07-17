package entities;

import models.NetHideMission;
import models.Triangulation;
import utils.SqliteUtils;
import utils.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NetHideMissionBai {

    private final File NET_MISSIONS_FILE, AI_GEO_DATA_FOLDER;
    private final List<NetHideMission> MISSIONS_LIST;
    private final List<Triangulation> AI_NAVIGATION_LIST;
    private final String ZONE_ID;
    private final boolean IS_HIDE, IS_DEBUG;
    private final String GEO_TYPE;
    private File missionsJson, aiNavigationJson;
    private RandomAccessFile missionRAF;

    public NetHideMissionBai(boolean isHide, File missionFile, String zoneId, File rootFolder, boolean isDebug, String geoType) throws Exception {
        this.IS_HIDE = isHide;
        this.NET_MISSIONS_FILE = missionFile;
        this.MISSIONS_LIST = new ArrayList<>();
        this.AI_NAVIGATION_LIST = new ArrayList<>();
        this.AI_GEO_DATA_FOLDER = new File(Files.createDirectories(Paths.get(rootFolder.getPath() + "\\AI GeoData\\" + zoneId)).toString());
        this.ZONE_ID = zoneId;
        this.IS_DEBUG = isDebug;
        this.GEO_TYPE = geoType;
        if (this.IS_DEBUG) {
            if (!Files.exists(Paths.get(this.AI_GEO_DATA_FOLDER + "\\AiNavigation.json"))) {
                this.aiNavigationJson = new File(Files.createFile(Paths.get(this.AI_GEO_DATA_FOLDER + "\\AiNavigation.json")).toString());
            }
            if (this.IS_HIDE) {
                this.missionsJson = new File(Files.createFile(Paths.get(this.AI_GEO_DATA_FOLDER + "\\HideMission.json")).toString());
            } else {
                this.missionsJson = new File(Files.createFile(Paths.get(this.AI_GEO_DATA_FOLDER + "\\NetMission.json")).toString());
            }
        }
    }

    public void readFile() throws Exception {
        initRandomAccessFile();
        Utils.readVersion(missionRAF);
        readData();
        Utils.closeRAF(missionRAF);
        export();
    }

    private void initRandomAccessFile() throws Exception {
        missionRAF = new RandomAccessFile(NET_MISSIONS_FILE, "r");
    }

    private void export() throws Exception {
        System.out.println("Total NET_HIDE_MISSIONS in " + ZONE_ID + " zone: " + MISSIONS_LIST.size());
        System.out.println("Total AI_NAVIGATION in " + ZONE_ID + " zone: " + AI_NAVIGATION_LIST.size());
        if (IS_DEBUG) {
            Utils.writeDataToJson(missionsJson, MISSIONS_LIST);
            Utils.writeDataToJson(aiNavigationJson, AI_NAVIGATION_LIST);
        } else {
            if (0 < MISSIONS_LIST.size())
                SqliteUtils.exportNetMissionData(IS_HIDE, AI_GEO_DATA_FOLDER, ZONE_ID, MISSIONS_LIST, GEO_TYPE);
            if (0 < AI_NAVIGATION_LIST.size())
                SqliteUtils.exportAiNavigationData(AI_GEO_DATA_FOLDER, ZONE_ID, AI_NAVIGATION_LIST, GEO_TYPE);
        }
    }

    private void readData() throws Exception {
        Utils.readCustomLength(missionRAF, 12);
        Utils.readCustomLength(missionRAF, 12);
        readNodes();
        readTriangulation();
    }

    private void readNodes() throws Exception {
        int nodesCount = Utils.readInt(missionRAF);
        for (int i = 0; i < nodesCount; i++) {
            int id = Utils.readInt(missionRAF);
            Utils.readCustomLength(missionRAF, 24);
            double x = Utils.readDouble(missionRAF);
            double y = Utils.readDouble(missionRAF);
            double z = Utils.readDouble(missionRAF);
            Utils.readCustomLength(missionRAF, 20);
            MISSIONS_LIST.add(new NetHideMission(id, x, y, z));
        }
    }

    private void readTriangulation() throws Exception {
        int triangulationCount = Utils.readInt(missionRAF);
        for (int i = 0; i < triangulationCount; i++) {
            int startId = Utils.readInt(missionRAF);
            int endId = Utils.readInt(missionRAF);
            double x = Utils.readDouble(missionRAF);
            double y = Utils.readDouble(missionRAF);
            double z = Utils.readDouble(missionRAF);
            Utils.readCustomLength(missionRAF, 24);
            Triangulation triangulation = new Triangulation(startId, endId, x, y, z);
            isExist(triangulation);
        }
    }

    private void isExist(Triangulation triangulation) {
        /*boolean isExists = false;
        for (Triangulation existsTriangulation : AI_NAVIGATION_LIST) {
            if (existsTriangulation.getStartPoint() == triangulation.getEndPoint() && existsTriangulation.getEndPoint() == triangulation.getStartPoint()) {
                isExists = true;
                break;
            }
        }
        if (!isExists)*/
        AI_NAVIGATION_LIST.add(triangulation);
    }

}
