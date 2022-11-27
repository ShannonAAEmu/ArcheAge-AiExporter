package reader;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BaiReader {

    void initReaderUtil() throws FileNotFoundException;

    void readFile();

    void checkVersion(int version);

    void readFromFile() throws IOException;

    void close();

    void prepareExport();

}
