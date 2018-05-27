package dao.impl;

import dao.FileDao;
import models.File;
import models.RightFile;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileDBDao implements FileDao {
    @Override
    public File getById(String id) {
        File file = new File();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from File where id=:fileId");
            query.setParameter("fileId", id);
            file = (File) query.getSingleResult();
        }
        catch (HibernateException er){
            System.out.println(er.getMessage());
        }
        finally {
            session.close();
        }
        return file;
    }

    @Override
    public List<File> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<File> fileList = new ArrayList<>();
        try{
            Query query = session.createQuery("from File");
            fileList = query.list();
        }
        catch (HibernateException er) {
            System.out.println(er.getMessage());
        }finally {
            session.close();
        }
        return fileList;
    }

    @Override
    public boolean add(File file, boolean isWrite) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(file);
            Set<User> users = file.getUser();
            for (User user : users){
                RightFile rightFile = new RightFile(user.getIdUser(), file.getIdFile(), isWrite);
                session.save(rightFile);
            }
            session.getTransaction().commit();
        }
        catch (HibernateException er){
            System.out.println(er.getMessage());
            result = false;
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean update(File file) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(file);
            session.getTransaction().commit();
        }catch (HibernateException er){
            System.out.println(er.getMessage());
            result = false;
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean delete(File file) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Set<User> users = file.getUser();
            session.delete(file);
            for (User user : users){
                RightFile rightFile = new RightFile(user.getIdUser(), file.getIdFile(), false);
                session.delete(rightFile);
            }
            session.getTransaction().commit();
        }
        catch (HibernateException er){
            System.out.println(er.getMessage()+" /n" +er.getStackTrace());
            result = false;
        }
        finally {
            session.close();
        }
        return result;
    }
}
