package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class WriterUtil {

    public static void writeDataToJson(File path, String name, Object object) throws IOException {
        File exportedFile = new File(path.getAbsolutePath() + "\\" + name);
        Files.createFile(exportedFile.toPath());
        RandomAccessFile randomAccessFile = new RandomAccessFile(exportedFile, "rw");
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
        String json;
        if (!object.getClass().equals(JSONArray.class)) {
            json = gson.toJson(object);
        } else {
            json = object.toString();
        }
        randomAccessFile.write(json.getBytes(StandardCharsets.UTF_8));
        randomAccessFile.close();
    }
}
