package server.services;

import dao.impl.ServerSettingsDBDao;

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
