package models;

import javax.persistence.*;

@Entity
@Table(name = "users_files")
public class UserFile {
    @Id
    @Column(name = "id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_file")
    private String idFile;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private String idUser;
    @Column(name ="write_right")
    private boolean writeRight;
    @Column(name = "read_right")
    private boolean readRight;

    public UserFile(String id, String idFile, String idUser, boolean writeRight, boolean readRight) {
        this.id = id;
        this.idFile = idFile;
        this.idUser = idUser;
        this.writeRight = writeRight;
        this.readRight = readRight;
    }

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public boolean isWriteRight() {
        return writeRight;
    }

    public void setWriteRight(boolean writeRight) {
        this.writeRight = writeRight;
    }

    public boolean isReadRight() {
        return readRight;
    }

    public void setReadRight(boolean readRight) {
        this.readRight = readRight;
    }
}
