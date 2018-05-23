package models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "id_user")
    private String idUser;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
//    @OneToMany()
//    private List<String> files;

    public User(String login, String password, String email) {
        this.idUser = UUID.randomUUID().toString();
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(String id, String login, String password, String email) {
        this.idUser = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(){ }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
