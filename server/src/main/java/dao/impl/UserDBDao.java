package dao.impl;

import models.File;
import util.*;
import dao.UserDao;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;

public class UserDBDao implements UserDao {


    @Override
    public User getById(String id) {
        User user = new User();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from User where id = :userId");
            query.setParameter("userId", id);
            user = (User) query.getSingleResult();
        }
        catch (HibernateException er){
            System.out.println(er.getMessage());
        }
        finally {
            session.close();
        }
        return user;
    }

    public User getByLogin(String id) {
        User user = new User();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from User where login = :userLogin");
            query.setParameter("userLogin", id);
            user = (User) query.getSingleResult();
        }
        catch (HibernateException er){
            System.out.println(er.getMessage());
        }
        finally {
            session.close();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> userList = new ArrayList<>();
        try{
            Query query = session.createQuery("from User");
            userList = query.list();
        }
        catch (HibernateException er) {
            System.out.println(er.getMessage());
        }finally {
            session.close();
        }
        return userList;
    }

    @Override
    public boolean add(User user){
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(user);
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
    public boolean update(User user) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(user);
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
    public List<User> getUserByLoginPass(String login, String pass){
        List<User> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from User where login = :paramLogin and password = :paramPass");
            query.setParameter("paramLogin", login);
            query.setParameter("paramPass", pass);
            users = query.list();
        }
        catch (HibernateException er){
            System.out.println(er.getMessage() +" /n"+er.getStackTrace());
        }
        finally {
            session.close();
        }
        return users;
    }

    @Override
    public boolean delete(User user) {
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(user);
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
