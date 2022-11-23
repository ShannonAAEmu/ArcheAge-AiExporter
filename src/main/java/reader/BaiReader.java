package reader;

import java.io.IOException;

public interface BaiReader {

    void read();

    int readFileVersion() throws IOException;

    void checkVersion(int version);

    void close();

    void print();

    void save();
}
