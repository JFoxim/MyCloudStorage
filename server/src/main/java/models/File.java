package models;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "files")
    private Set<User> users = new HashSet<>();

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

    public File(){  }

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

    public Set<User> getUser() {
        return users;
    }

    public void setUser(Set<User> user) {
        this.users = user;
    }
}


