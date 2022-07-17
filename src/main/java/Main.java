import entities.AreasMissionBai;
import entities.NetHideMissionBai;
import entities.VertexMissionBai;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {

    private static final File ROOT_FOLDER = new File(System.getProperty("user.dir"));
    private static final boolean IS_DEBUG = false;
    private static String geoType = "server";

    public static void main(String[] args) throws Exception {
        HashMap<File, String> serverBaiHashMap = new HashMap<>();
        HashMap<File, String> clientBaiHashMap = new HashMap<>();
        File worldsFolder = new File(String.valueOf(ROOT_FOLDER));
        for (File world : Objects.requireNonNull(worldsFolder.listFiles())) {
            if (world.getName().startsWith("instance_") || world.getName().equals("arche_mall_world")) {
                if (!world.getName().endsWith("_dev")) {
                    File zoneFolder = new File(world.getPath() + "\\zone");
                    for (File id : Objects.requireNonNull(zoneFolder.listFiles())) {
                        if (NumberUtils.isDigits(id.getName())) {
                            for (File file : Objects.requireNonNull(id.listFiles())) {
                                if (FilenameUtils.getExtension(file.getName()).equals("bai")) {
                                    if (4 < file.length()) {
                                        serverBaiHashMap.put(file, id.getName());
                                    }
                                }
                            }
                        }
                    }
                    File pathsFolder = new File(world.getPath() + "\\paths");
                    for (File ids : Objects.requireNonNull(pathsFolder.listFiles())) {
                        for (File file : Objects.requireNonNull(ids.listFiles())) {
                            if (FilenameUtils.getExtension(file.getName()).equals("bai")) {
                                if (4 < file.length()) {
                                    clientBaiHashMap.put(file, ids.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Total server zone data: " + serverBaiHashMap.size());
        readGeoData(serverBaiHashMap);
        //geoType = "client";
        //System.out.println("Total client zone data: " + clientBaiHashMap.size());
        //readGeoData(clientBaiHashMap);
    }

    private static void readGeoData(HashMap<File, String> baiHashMap) throws Exception {
        for (Map.Entry<File, String> map : baiHashMap.entrySet()) {
            if ("areasmission0.bai".equals(map.getKey().getName())) {
                new AreasMissionBai(map.getKey(), map.getValue(), ROOT_FOLDER, IS_DEBUG, geoType).readFile();
            }
            if ("netmission0.bai".equals(map.getKey().getName()) || "hidemission0.bai".equals(map.getKey().getName())) {
                boolean isHide = "hidemission0.bai".equals(map.getKey().getName());
                new NetHideMissionBai(isHide, map.getKey(), map.getValue(), ROOT_FOLDER, IS_DEBUG, geoType).readFile();
            }
            if ("vertsmission0.bai".equals(map.getKey().getName())) {
                new VertexMissionBai(map.getKey(), map.getValue(), ROOT_FOLDER, IS_DEBUG, geoType).readFile();
            }
        }
    }

}
