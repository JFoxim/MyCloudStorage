package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "server_settings")
public class ServerSettings {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name ="name")
    private String name;
    @Column(name = "value")
    private String value;

    public ServerSettings(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public ServerSettings() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
