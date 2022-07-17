package entities;

import models.PointPos;
import utils.SqliteUtils;
import utils.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VertexMissionBai {

    private final File VERTEX_MISSIONS_FILE, AI_GEO_DATA_FOLDER;
    private final List<PointPos> VERTEX_MISSIONS_LIST;
    private final String ZONE_ID;
    private final boolean IS_DEBUG;
    private final String GEO_TYPE;
    private File VERTEX_MISSIONS_JSON;
    private RandomAccessFile vertexMissionRAF;

    public VertexMissionBai(File missionFile, String zoneId, File rootFolder, boolean isDebug, String geoType) throws Exception {
        this.VERTEX_MISSIONS_FILE = missionFile;
        this.VERTEX_MISSIONS_LIST = new ArrayList<>();
        this.AI_GEO_DATA_FOLDER = new File(Files.createDirectories(Paths.get(rootFolder.getPath() + "\\AI GeoData\\" + zoneId)).toString());
        this.ZONE_ID = zoneId;
        this.IS_DEBUG = isDebug;
        this.GEO_TYPE = geoType;
        if (this.IS_DEBUG) {
            this.VERTEX_MISSIONS_JSON = new File(Files.createFile(Paths.get(this.AI_GEO_DATA_FOLDER + "\\VertexMission.json")).toString());
        }
    }

    public void readFile() throws Exception {
        initRandomAccessFile();
        Utils.readVersion(vertexMissionRAF);
        readData();
        Utils.closeRAF(vertexMissionRAF);
        export();
    }

    private void initRandomAccessFile() throws Exception {
        vertexMissionRAF = new RandomAccessFile(VERTEX_MISSIONS_FILE, "r");
    }

    private void export() throws Exception {
        System.out.println("Total VERTEX_MISSIONS in " + ZONE_ID + " zone: " + VERTEX_MISSIONS_LIST.size());
        if (IS_DEBUG) {
            Utils.writeDataToJson(VERTEX_MISSIONS_JSON, VERTEX_MISSIONS_LIST);
        } else {
            if (0 < VERTEX_MISSIONS_LIST.size())
                SqliteUtils.exportVertexMissionData(AI_GEO_DATA_FOLDER, ZONE_ID, VERTEX_MISSIONS_LIST, GEO_TYPE);
        }
    }

    private void readData() throws Exception {
        int vertexCount = Utils.readInt(vertexMissionRAF);
        for (int i = 0; i < vertexCount; i++) {
            double x = Utils.readDouble(vertexMissionRAF);
            double y = Utils.readDouble(vertexMissionRAF);
            double z = Utils.readDouble(vertexMissionRAF);
            Utils.readCustomLength(vertexMissionRAF, 20);
            VERTEX_MISSIONS_LIST.add(new PointPos(x, y, z));
        }
    }
}
