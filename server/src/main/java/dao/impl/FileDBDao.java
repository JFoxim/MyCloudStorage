package dao.impl;

import dao.FileDao;
import models.File;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;

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
    public List<File> getByUserId(String userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<File> fileList = new ArrayList<>();
        try{
//            Query query = session.createQuery("select f.id, f.path, f.dt_create, f.description from files f " +
//                    "inner join users_files uf on f.id=uf.files_id  " +
//                    "where uf.user_id='"+userId+"'");
            Query query = session.createQuery("from File as f inner join UserFile as uf f.idFile where uf.idUser=:userId");
            query.setParameter("userId", userId);
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
    public boolean add(File file, String userId) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(file);
            //session.save();
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
        return false;
    }

    @Override
    public boolean delete(File file) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(file);
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
