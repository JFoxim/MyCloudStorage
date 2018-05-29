package app.models;

import javafx.beans.property.SimpleStringProperty;

public class FileData {

    public FileData(String id, String fileName, String size){
        this.id = new SimpleStringProperty(id);
        this.fileName = new SimpleStringProperty(fileName);
        this.size = new SimpleStringProperty(size);
    }

    public FileData(){}

    private SimpleStringProperty id;
    private SimpleStringProperty fileName;
    private SimpleStringProperty size;

    public SimpleStringProperty getFileName() {
        return fileName;
    }

    public void setFileName(SimpleStringProperty fileName) {
        this.fileName = fileName;
    }

    public SimpleStringProperty getSize() {
        return size;
    }

    public void setSize(SimpleStringProperty size) {
        this.size = size;
    }

    public SimpleStringProperty getId() {
        return id;
    }

    public void setId(SimpleStringProperty id) {
        this.id = id;
    }
}
