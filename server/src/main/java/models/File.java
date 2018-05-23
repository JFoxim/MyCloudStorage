package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "files")
public class File {
    @Id
    @Column(name = "id_file")
    private String idFile;
    @Column(name ="path")
    private String path;
    @Column(name = "dt_create")
    private Date dtCreate;
    @Column(name = "description")
    private String description;

    public File(String path, String description) {
        this.path = path;
        this.idFile = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.dtCreate = new Date();// LocalDateTime.now();
        this.description = description;
    }

    public File(String id, String path, String description) {
        this.path = path;
        this.idFile = id;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.dtCreate = new Date();// LocalDateTime.now();
        this.description = description;
    }

    public File(){
    }

    public String getIdFile() {
        return idFile;
    }

    public Date getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Date dtCreate) {
        this.dtCreate = dtCreate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
