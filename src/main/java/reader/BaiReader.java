package reader;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BaiReader {

    void initReaderUtil() throws FileNotFoundException;

    void readFile();

    void checkVersion(int version);

    boolean isValidDescriptor(long count);

    void readDescriptor(long size) throws IOException;

    void close();

    void print();

    void save();
}
