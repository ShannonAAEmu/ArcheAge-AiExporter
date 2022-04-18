package entities;

import models.NodeMission;
import models.Triangulation;
import utils.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class NetMissionBai {

    private final File NET_MISSIONS_FILE, NET_MISSIONS_JSON, AI_NAVIGATION_JSON;
    private final List<NodeMission> NET_MISSIONS;
    private final List<Triangulation> AI_NAVIGATION;
    private int nodesCount, triangulationEdgesCount;
    private RandomAccessFile netMissionRAF;

    public NetMissionBai(File missionFile, String path) throws Exception {
        this.NET_MISSIONS_FILE = missionFile;
        this.NET_MISSIONS = new ArrayList<>();
        this.AI_NAVIGATION = new ArrayList<>();
        new File(path + "\\Export AI").mkdir();
        this.NET_MISSIONS_JSON = new File(path + "\\Export AI\\NetMission.json");
        this.AI_NAVIGATION_JSON = new File(path + "\\Export AI\\AiNavigation.json");
        this.NET_MISSIONS_JSON.createNewFile();
    }

    public void readFile() throws Exception {
        initRandomAccessFile();
        Utils.readVersion(netMissionRAF);
        readData();
        assert NET_MISSIONS != null;
        Utils.writeDataToJson(NET_MISSIONS_JSON, NET_MISSIONS);
        assert AI_NAVIGATION != null;
        Utils.writeDataToJson(AI_NAVIGATION_JSON, AI_NAVIGATION);
        Utils.closeRAF(netMissionRAF);
    }

    private void initRandomAccessFile() throws Exception {
        netMissionRAF = new RandomAccessFile(NET_MISSIONS_FILE, "r");
    }

    private void readData() throws Exception {
        Utils.readCustomLength(netMissionRAF, 12);
        Utils.readCustomLength(netMissionRAF, 12);
        readNodesCount();
        readNodes();
        readTriangulationEdgesCount();
        readTriangulationEdges();
    }

    private void readNodesCount() throws Exception {
        nodesCount = Utils.readInt(netMissionRAF);
    }

    private void readNodes() throws Exception {
        for (int i = 0; i < nodesCount; i++) {
            int id = Utils.readInt(netMissionRAF);
            Utils.readCustomLength(netMissionRAF, 24);
            double x = Utils.readDouble(netMissionRAF);
            double y = Utils.readDouble(netMissionRAF);
            double z = Utils.readDouble(netMissionRAF);
            Utils.readCustomLength(netMissionRAF, 20);
            NET_MISSIONS.add(new NodeMission(id, x, y, z));
        }
    }

    private void readTriangulationEdgesCount() throws Exception {
        triangulationEdgesCount = Utils.readInt(netMissionRAF);
    }

    private void readTriangulationEdges() throws Exception {
        for (int i = 0; i < triangulationEdgesCount; i++) {
            int sourceId = Utils.readInt(netMissionRAF);
            int targetId = Utils.readInt(netMissionRAF);
            double x = Utils.readDouble(netMissionRAF);
            double y = Utils.readDouble(netMissionRAF);
            double z = Utils.readDouble(netMissionRAF);
            Utils.readCustomLength(netMissionRAF, 24);
            Triangulation triangulation = new Triangulation(sourceId, targetId, x, y, z);
            AI_NAVIGATION.add(triangulation);
        }
    }
}
