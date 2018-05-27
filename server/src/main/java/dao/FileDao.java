package dao;

import models.File;
import models.User;

import java.util.List;

public interface FileDao {
    File getById(String id);
    List<File> getAll();
    boolean add(File file, boolean isWrite);
    boolean update(File file);
    boolean delete(File file);
}
