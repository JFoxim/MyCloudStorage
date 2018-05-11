package dao;

import models.User;

import java.util.List;

public interface UserDao{

    User getById(String id);
    List<User> getAll();
    boolean addUser(User user);
    boolean updateUser(User user);

}