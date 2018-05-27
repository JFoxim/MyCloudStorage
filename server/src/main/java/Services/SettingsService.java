package Services;

import dao.impl.ServerSettingsDBDao;
import models.ServerSettings;
import java.util.ArrayList;
import java.util.List;

public class SettingsService {

    private static String serverPath;

    public String getPath(){
        if(serverPath == null || serverPath.isEmpty()) {
            ServerSettingsDBDao serverSettingsDBDao = new ServerSettingsDBDao();
            serverPath = serverSettingsDBDao.getById(1).getValue();
        }
        return serverPath;
    }
}
