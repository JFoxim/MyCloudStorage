package dao;

import models.File;
import models.ServerSettings;

import java.util.List;

public interface ServerSettingsDao {
    ServerSettings getById(int id);
    List<ServerSettings> getAll();
}
