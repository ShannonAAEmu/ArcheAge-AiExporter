package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.NumberFormat;

public class Utils {

    private static final NumberFormat numberFormat;
    private static final StringBuilder sb;

    static {
        numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        sb = new StringBuilder();
    }

    public static byte[] reverseBytesArray(byte[] bytes) {
        byte[] reverseArray = new byte[bytes.length];
        int j = bytes.length;
        for (byte b : bytes) {
            reverseArray[j - 1] = b;
            j = j - 1;
        }
        return reverseArray;
    }

    public static int convertHexToInt(byte[] bytes) {
        bytes = reverseBytesArray(bytes);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        String idxStr = sb.toString();
        clearSB(sb);
        if ("ffffffff".equals(idxStr)) {
            return 0;
        }
        return Integer.parseInt(idxStr, 16);
    }

    public static double convertHexToDouble(byte[] bytes) {
        long hexInLong = convertHexToLong(bytes);
        clearSB(sb);
        numberFormat.setMinimumFractionDigits(2);
        Float f = Float.intBitsToFloat(((int) hexInLong));
        String strFloat = numberFormat.format(f);
        String convertFloat = String.format("%.2f", Double.valueOf(strFloat));
        return Double.parseDouble(convertFloat);
    }

    private static Long convertHexToLong(byte[] bytes) {
        byte[] bytesMass = reverseBytesArray(bytes);
        for (byte b : bytesMass) {
            sb.append(String.format("%02x", b));
        }
        Long parseLong = Long.parseLong(sb.toString(), 16);
        clearSB(sb);
        return parseLong;
    }

    public static byte[] readCustomLength(RandomAccessFile randomAccessFile, int length) throws Exception {
        byte[] bytes = new byte[length];
        randomAccessFile.read(bytes);
        return bytes;
    }

    public static int readInt(RandomAccessFile randomAccessFile) throws Exception {
        byte[] bytes = new byte[4];
        randomAccessFile.read(bytes);
        return convertHexToInt(bytes);
    }

    public static double readDouble(RandomAccessFile randomAccessFile) throws Exception {
        byte[] bytes = new byte[4];
        randomAccessFile.read(bytes);
        return convertHexToDouble(bytes);
    }

    public static String readString(RandomAccessFile randomAccessFile, int length) throws Exception {
        byte[] bytes = new byte[length];
        randomAccessFile.read(bytes);
        return new String(bytes);
    }

    public static void readVersion(RandomAccessFile randomAccessFile) throws Exception {
        readInt(randomAccessFile);
    }

    public static void writeDataToJson(File jsonFile, Object object) throws Exception {
        Files.deleteIfExists(jsonFile.toPath());
        RandomAccessFile randomAccessFile = new RandomAccessFile(jsonFile, "rw");
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

    public static void clearSB(StringBuilder sb) {
        sb.setLength(0);
    }

    public static void closeRAF(RandomAccessFile randomAccessFile) throws Exception {
        randomAccessFile.close();
    }

}
