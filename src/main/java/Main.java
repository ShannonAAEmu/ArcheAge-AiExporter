import entities.AreasMissionBai;
import entities.NetMissionBai;
import entities.VertexMissionBai;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {

    private static final File rootFolder = new File(System.getProperty("user.dir"));

    public static void main(String[] args) throws Exception {
        //File instancesFolder = new File(rootFolder + "\\arche_mall_world");  // debug
        File zoneFolder = new File(rootFolder + "\\zone");
        HashMap<File, String> baiHashMap = new HashMap<>();
        for (File id : Objects.requireNonNull(zoneFolder.listFiles())) {
            if (NumberUtils.isDigits(id.getName())) {
                for (File file : Objects.requireNonNull(id.listFiles())) {
                    if (FilenameUtils.getExtension(file.getName()).equals("bai")) {
                        if (4 < file.length()) {
                            baiHashMap.put(file, id.getPath());
                        }
                    }
                }
            }
        }
        for (Map.Entry<File, String> map : baiHashMap.entrySet()) {
            if ("areasmission0.bai".equals(map.getKey().getName())) {
                new AreasMissionBai(map.getKey(), map.getValue()).readFile();
            }
            if ("netmission0.bai".equals(map.getKey().getName())) {
                new NetMissionBai(map.getKey(), map.getValue()).readFile();
            }
            if ("vertsmission0.bai".equals(map.getKey().getName())) {
                new VertexMissionBai(map.getKey(), map.getValue()).readFile();
            }
        }
    }

}
