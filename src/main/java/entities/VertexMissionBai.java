package entities;

import models.PointPos;
import utils.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class VertexMissionBai {

    private final File VERTEX_MISSIONS_FILE, VERTEX_MISSIONS_JSON;
    private final List<PointPos> VERTEX_MISSIONS;
    private int vertexCount;
    private RandomAccessFile vertexMissionRAF;

    public VertexMissionBai(File missionFile, String path) throws Exception {
        this.VERTEX_MISSIONS_FILE = missionFile;
        this.VERTEX_MISSIONS = new ArrayList<>();
        new File(path + "\\Export AI").mkdir();
        this.VERTEX_MISSIONS_JSON = new File(path + "\\Export AI\\VertexMission.json");
        this.VERTEX_MISSIONS_JSON.createNewFile();
    }

    public void readFile() throws Exception {
        initRandomAccessFile();
        Utils.readVersion(vertexMissionRAF);
        readData();
        assert VERTEX_MISSIONS != null;
        Utils.writeDataToJson(VERTEX_MISSIONS_JSON, VERTEX_MISSIONS);
        Utils.closeRAF(vertexMissionRAF);
    }

    private void initRandomAccessFile() throws Exception {
        vertexMissionRAF = new RandomAccessFile(VERTEX_MISSIONS_FILE, "r");
    }

    private void readData() throws Exception {
        readVertexCount();
        readVertexMissions();
    }

    private void readVertexCount() throws Exception {
        vertexCount = Utils.readInt(vertexMissionRAF);
    }

    private void readVertexMissions() throws Exception {
        for (int i = 0; i < vertexCount; i++) {
            double x = Utils.readDouble(vertexMissionRAF);
            double y = Utils.readDouble(vertexMissionRAF);
            double z = Utils.readDouble(vertexMissionRAF);
            Utils.readCustomLength(vertexMissionRAF, 20);
            VERTEX_MISSIONS.add(new PointPos(x, y, z));
        }
    }
}
