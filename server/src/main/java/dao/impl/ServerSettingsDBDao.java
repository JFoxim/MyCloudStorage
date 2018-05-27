package dao.impl;

import dao.ServerSettingsDao;
import models.ServerSettings;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ServerSettingsDBDao implements ServerSettingsDao {

    @Override
    public ServerSettings getById(int id) {
        ServerSettings serverSettings = new ServerSettings();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from ServerSettings where id=:settingsId");
            query.setParameter("settingsId", id);
            serverSettings = (ServerSettings) query.getSingleResult();
        }
        catch (HibernateException er){
            //System.out.println(er.getMessage());
        }
        finally {
            session.close();
        }
        return serverSettings;
    }

    @Override
    public List<ServerSettings> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ServerSettings> serverSettingsList = new ArrayList<>();
        try{
            Query query = session.createQuery("from ServerSettings");
            serverSettingsList = query.list();
        }
        catch (HibernateException er) {
            System.out.println(er.getMessage());
        }finally {
            session.close();
        }
        return serverSettingsList;
    }
}
