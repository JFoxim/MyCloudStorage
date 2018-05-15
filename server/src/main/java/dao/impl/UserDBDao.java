package dao.impl;

import dao.HibernateHelper;
import dao.UserDao;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDBDao implements UserDao {


    @Override
    public User getById(String id) {
        User user = new User();
        Session session = HibernateHelper.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from User where id = :paramId");
            query.setParameter("paramId", id);
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
        Session session = HibernateHelper.getSessionFactory().openSession();
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
    public boolean addUser(User user){
        boolean result = true;
        Session session = HibernateHelper.getSessionFactory().openSession();
        try {
            Query query = session.createQuery(String.format("INSERT INTO User (id, login, password, email) select '%s', '%s', '%s', '%s'",
                    user.getId(), user.getLogin(), user.getPassword(), user.getEmail()));
            query.executeUpdate();
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
    public boolean updateUser(User user) {
        boolean result = true;
        Session session = HibernateHelper.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("UPDATE User SET login = :login, password = :password, email= :email WHERE id =: userId");
            query.setParameter("login", user.getLogin());
            query.setParameter("password", user.getPassword());
            query.setParameter("email", user.getEmail());
            query.setParameter("userId", user.getId());
        }catch (HibernateException er){
            System.out.println(er.getMessage());
            result = false;
        }
        finally {
            session.close();
        }
        return result;
    }

    public static User getUserByLoginPass(String login, String pass){
        User user = new User();
        Session session = HibernateHelper.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from User where login = :paramLogin and password= :paramPass");
            query.setParameter("paramLogin", login);
            query.setParameter("paramPass", pass);
            user = (User) query.getSingleResult();
        }
        catch (HibernateException er){
            System.out.println(er.getMessage() +" /n"+er.getStackTrace());
        }
        finally {
            session.close();
        }
        return user;
    }


//    public static String getNickByLoginPass(String login, String pass) {
////        try {
//////            int passHash = pass.hashCode();
//////            ResultSet rs = stmt.executeQuery(String.format("SELECT nick FROM users WHERE login = '%s' AND password = '%d';", login, passHash));
//////            if (rs.next()) {
//////                return rs.getString(1);
//////            }
//////        } catch (SQLException e) {
//////            e.printStackTrace();
//////        }
//    }

}
