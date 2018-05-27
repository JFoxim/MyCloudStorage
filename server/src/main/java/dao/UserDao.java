package dao;

import models.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.UUID;

public interface UserDao{

    User getById(String id);
    List<User> getAll();
    boolean add(User user);
    boolean update(User user);
    User getUserByLoginPass(String login, String pass);
    boolean delete(User user);

}