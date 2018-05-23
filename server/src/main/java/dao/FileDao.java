package dao;

import models.File;
import models.User;

import java.util.List;

public interface FileDao {
    File getById(String id);
    List<File> getAll();
    List<File> getByUserId(String userId);
    boolean add(File file, String userId);
    boolean update(File file);
    boolean delete(File file);
}
