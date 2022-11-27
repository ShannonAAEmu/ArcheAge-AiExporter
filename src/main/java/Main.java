import entities.export.*;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import reader.BaiReader;
import reader.impl.*;
import utils.WriterUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {

    private static final File ROOT_FOLDER = new File(System.getProperty("user.dir"));
    private static HashMap<File, String> serverBaiHashMap;

    public static void main(String[] args) {
        serverBaiHashMap = new HashMap<>();
        checkFolders();
        try {
            exportNavData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkFolders() {
        File worlds = new File(String.valueOf(ROOT_FOLDER));
        for (File world : Objects.requireNonNull(worlds.listFiles())) {
            if (world.getName().startsWith("instance_") || world.getName().equals("arche_mall_world")) {
                checkInstancesFolders(world);
            }
        }
    }

    private static void checkInstancesFolders(File world) {
        if (!world.getName().endsWith("_dev")) {
            checkServerFolders(world);
        }
    }

    private static void checkServerFolders(File world) {
        File zone = new File(world.getPath() + "\\zone");
        for (File zoneId : Objects.requireNonNull(zone.listFiles())) {
            checkZoneFolders(zoneId);
        }
    }

    private static void checkZoneFolders(File zoneId) {
        if (NumberUtils.isDigits(zoneId.getName())) {
            for (File file : Objects.requireNonNull(zoneId.listFiles())) {
                loadServerBaiFiles(file, zoneId);
            }
        }
    }

    private static void loadServerBaiFiles(File file, File zoneId) {
        if (FilenameUtils.getExtension(file.getName()).equals("bai")) {
            serverBaiHashMap.put(file, zoneId.getName());
        }
    }

    private static void exportNavData() throws IOException {
        String exportFolder = ROOT_FOLDER + "\\export_server";
        File path = new File(exportFolder);
        createExportDirectory(path);
        File zonePath;
        BaiReader baiReader;
        int total = serverBaiHashMap.size();
        int i = 0;
        int prev = 0;
        int cur;
        AreasMissionExport areasMissionExport;
        FlightMissionExport flightMissionExport;
        NetMissionExport netMissionExport;
        RoadMissionExport roadMissionExport;
        VertexMissionExport vertexMissionExport;
        WaypointSurfaceNavigationExport waypointSurfaceNavigationExport;
        for (Map.Entry<File, String> map : serverBaiHashMap.entrySet()) {
            int zoneId = Integer.parseInt(map.getValue());
            zonePath = new File(path.getPath() + "\\" + zoneId);
            createExportZoneDirectory(zonePath);
            if ("areasmission0.bai".equals(map.getKey().getName())) {
                areasMissionExport = new AreasMissionExport();
                baiReader = new AreasMissionReaderImpl(areasMissionExport, map.getKey(), zoneId);
                baiReader.readFile();
                baiReader.prepareExport();
                exportData(zonePath, "forbidden_areas", areasMissionExport.getForbiddenAreasList());
                exportData(zonePath, "navigation_modifiers", areasMissionExport.getNavigationModifiersList());
                exportData(zonePath, "designer_forbidden_areas", areasMissionExport.getDesignerForbiddenAreasList());
                exportData(zonePath, "forbidden_boundaries", areasMissionExport.getForbiddenBoundariesList());
                exportData(zonePath, "extra_link_costs", areasMissionExport.getExtraLinkCostsList());
                exportData(zonePath, "designer_paths", areasMissionExport.getDesignerPathsList());
            }
            if ("fnavmission0.bai".equals(map.getKey().getName())) {
                flightMissionExport = new FlightMissionExport();
                baiReader = new FlightMissionReaderImpl(flightMissionExport, map.getKey(), zoneId);
                baiReader.readFile();
                baiReader.prepareExport();
                exportData(zonePath, "flight_navi_regions", flightMissionExport.getFlightNavRegion());
            }
            if ("netmission0.bai".equals(map.getKey().getName())) {
                netMissionExport = new NetMissionExport();
                baiReader = new NetMissionReaderImpl(netMissionExport, map.getKey(), zoneId);
                baiReader.readFile();
                baiReader.prepareExport();
                exportData(zonePath, "triangulation", netMissionExport.getNavigations());
            }
            if ("roadnavmission0.bai".equals(map.getKey().getName())) {
                roadMissionExport = new RoadMissionExport();
                baiReader = new RoadMissionImpl(roadMissionExport, map.getKey(), zoneId);
                baiReader.readFile();
                baiReader.prepareExport();
                exportData(zonePath, "roads", roadMissionExport.getRoadList());
            }
            // v3dmission0.bai
            if ("vertsmission0.bai".equals(map.getKey().getName())) {
                vertexMissionExport = new VertexMissionExport();
                baiReader = new VertexMissionReaderImpl(vertexMissionExport, map.getKey(), zoneId);
                baiReader.readFile();
                baiReader.prepareExport();
                exportData(zonePath, "vertex", vertexMissionExport.getObstacleDataDescriptorList());
            }
            if ("waypt3dsfcmission0.bai".equals(map.getKey().getName())) {
                waypointSurfaceNavigationExport = new WaypointSurfaceNavigationExport();
                baiReader = new WaypointSurfaceNavigationReaderImpl(waypointSurfaceNavigationExport, map.getKey(), zoneId);
                baiReader.readFile();
                baiReader.prepareExport();
                exportData(zonePath, "waypoint_surface_nav", waypointSurfaceNavigationExport.getWaypointSurfaceNavigationList());
            }
            cur = i++ * 100 / total;
            if (prev != cur) {
                prev = cur;
                System.out.println(cur + "% ");
            }
        }
    }

    private static void exportData(File zonePath, String fileName, Object export) throws IOException {
        WriterUtil.writeDataToJson(zonePath, fileName + ".json", export);
    }

    private static void createExportDirectory(File exportFolder) throws IOException {
        FileDeleteStrategy.FORCE.delete(exportFolder);
        Files.createDirectory(exportFolder.toPath());
    }

    private static void createExportZoneDirectory(File zoneFolder) throws IOException {
        if (!zoneFolder.exists()) {
            Files.createDirectory(zoneFolder.toPath());
        }
    }
}
