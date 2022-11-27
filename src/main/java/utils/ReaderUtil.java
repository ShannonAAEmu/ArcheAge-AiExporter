package utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.NumberFormat;
import java.util.Objects;

public class ReaderUtil {
    private static final NumberFormat NUMBER_FORMAT;
    private static final StringBuilder SB;

    static {
        NUMBER_FORMAT = NumberFormat.getInstance();
        NUMBER_FORMAT.setGroupingUsed(false);
        SB = new StringBuilder();
    }

    private final RandomAccessFile randomAccessFile;

    public ReaderUtil(RandomAccessFile randomAccessFile) {
        this.randomAccessFile = randomAccessFile;
    }

    public long getPos() {
        try {
            return randomAccessFile.getFilePointer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] reverseBytesArray(byte[] bytes) {
        byte[] reverseArray = new byte[bytes.length];
        int j = bytes.length;
        for (byte b : bytes) {
            reverseArray[j - 1] = b;
            j = j - 1;
        }
        return reverseArray;
    }

    private short convertHexToShort(byte[] bytes) {
        formatStringBuilder(bytes);
        String idxStr = SB.toString();
        clearSB();
        if ("ffffffff".equals(idxStr)) {
            return 0;
        }
        return Short.parseShort(idxStr, 16);
    }

    private int convertHexToInt(byte[] bytes) {
        formatStringBuilder(bytes);
        String idxStr = SB.toString();
        clearSB();
        if ("ffffffff".equals(idxStr)) {
            return 0;
        }
        return Integer.parseInt(idxStr, 16);
    }

    private long convertHexToLong(byte[] bytes) {
        formatStringBuilder(bytes);
        long parseLong = Long.parseLong(SB.toString(), 16);
        clearSB();
        return parseLong;
    }

    private void formatStringBuilder(byte[] bytes) {
        bytes = reverseBytesArray(bytes);
        for (byte b : bytes) {
            SB.append(String.format("%02x", b));
        }
    }

    private double convertHexToDouble(byte[] bytes) {
        long hexInLong = convertHexToLong(bytes);
        NUMBER_FORMAT.setMinimumFractionDigits(2);
        Float f = Float.intBitsToFloat(((int) hexInLong));
        String strFloat = NUMBER_FORMAT.format(f);
        String convertFloat = String.format("%.2f", Double.valueOf(strFloat));
        return Double.parseDouble(convertFloat);
    }

    public byte readByte() throws IOException {
        byte[] bytes = new byte[1];
        randomAccessFile.read(bytes);
        return bytes[0];
    }

    public short readShot() throws IOException {
        byte[] bytes = new byte[2];
        randomAccessFile.read(bytes);
        return convertHexToShort(bytes);
    }

    public int readInt() throws IOException {
        byte[] bytes = new byte[4];
        randomAccessFile.read(bytes);
        return convertHexToInt(bytes);
    }

    public long readLong() throws IOException {
        byte[] bytes = new byte[8];
        randomAccessFile.read(bytes);
        return convertHexToLong(bytes);
    }

    public double readDouble() throws IOException {
        byte[] bytes = new byte[4];
        randomAccessFile.read(bytes);
        return convertHexToDouble(bytes);
    }

    public String readString(int length) throws IOException {
        byte[] bytes = new byte[length];
        randomAccessFile.read(bytes);
        return new String(bytes);
    }

    public int readFileVersion() throws IOException {
        return readInt();
    }

    public long readUnsigned() throws IOException {
        return readInt();
    }

    public void closeRAF() throws IOException {
        randomAccessFile.close();
    }

    private void clearSB() {
        SB.setLength(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReaderUtil that = (ReaderUtil) o;
        return Objects.equals(randomAccessFile, that.randomAccessFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(randomAccessFile);
    }

    @Override
    public String toString() {
        return "ReaderUtil{" +
                "randomAccessFile=" + randomAccessFile +
                '}';
    }
}
