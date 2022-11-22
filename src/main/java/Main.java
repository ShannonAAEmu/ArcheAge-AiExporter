import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import reader.BaiReader;
import reader.impl.VertsMissionReaderImpl;

import java.io.File;
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
            loadServerGeoData();
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
            File zone = new File(world.getPath() + "\\zone");
            for (File zoneId : Objects.requireNonNull(zone.listFiles())) {
                checkZoneFolders(zoneId);
            }
        }
    }

    private static void checkZoneFolders(File zoneId) {
        if (NumberUtils.isDigits(zoneId.getName())) {
            for (File file : Objects.requireNonNull(zoneId.listFiles())) {
                loadBaiFiles(file, zoneId);
            }
        }
    }

    private static void loadBaiFiles(File file, File zoneId) {
        if (FilenameUtils.getExtension(file.getName()).equals("bai")) {
            serverBaiHashMap.put(file, zoneId.getName());
        }
    }

    private static void loadServerGeoData() {
        BaiReader baiReader = null;
        for (Map.Entry<File, String> map : serverBaiHashMap.entrySet()) {
            if ("vertsmission0.bai".equals(map.getKey().getName())) {
                baiReader = new VertsMissionReaderImpl(map.getKey(), Integer.parseInt(map.getValue()), ROOT_FOLDER);
                baiReader.read();
                baiReader.print();
                baiReader.close();
            }
        }
    }
}
