package dao.impl;

import dao.HibernateHelper;
import dao.UserDao;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDBDao implements UserDao {


    @Override
    public User getById(String id) {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from User where id = :paramName");
        query.setParameter("paramName", id);
        User user = (User)query.getSingleResult();
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = HibernateHelper.getSessionFactory().openSession();
        Query query = session.createQuery("from User");
        List<User> userList = query.list();
        session.close();
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
            result = false;
        }
        return result;
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
