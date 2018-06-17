package common.messages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileMessage extends AbstractMessage {
    private String filename;
    private long size;
    private byte[] data;
    private int partsCount;
    private int partNumber;

    public  FileMessage(Path path) throws IOException {
        filename = path.getFileName().toString();
        size = Files.size(path);
        data = Files.readAllBytes(path);

    }

    public FileMessage(String filename, byte[] data, int partsCount, int partNumber){
        this.filename = filename;
        this.partNumber = partNumber;
        this.partsCount = partsCount;
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public int getPartNumber() {
        return partNumber;
    }
}
