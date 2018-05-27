package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "users_files")
public class RightFile {
    @EmbeddedId
    private UserFile key;

    @Column(name = "write_right")
    private boolean isWrite;

    @Column(name = "read_right")
    private boolean isRead;

    public RightFile(String userId, String fileId, boolean isWrite){
        key = new UserFile();
        key.userId = userId;
        key.fileId = fileId;
        this.isWrite = isWrite;
        this.isRead = true;
    }

    public RightFile(){}

    public UserFile getKey() {
        return key;
    }

    public void setKey(UserFile key) {
        this.key = key;
    }

    public boolean isWrite() {
        return isWrite;
    }

    public void setWrite(boolean write) {
        isWrite = write;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Embeddable
    public class UserFile implements Serializable {
        static final long serialVersionUID = 1L;

        @Column(name ="id_user")
        private String userId;

        @Column(name = "id_file")
        private String fileId;

        @Override
        public int hashCode() {
            return UUID.fromString(userId).hashCode()+UUID.fromString(fileId).hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            UserFile userFile = (UserFile)o;
            return  (!userId.equals(userFile.userId)
                    && !fileId.equals(userFile.fileId));
        }
    }
}


