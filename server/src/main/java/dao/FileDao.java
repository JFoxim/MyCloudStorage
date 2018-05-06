package dao;

import models.User;

import java.util.List;

public interface FileDao {
    User getById(long id);
    List<User> getUserAll();
}
