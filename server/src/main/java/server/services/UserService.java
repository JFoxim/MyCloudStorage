package server.services;

import dao.impl.UserDBDao;
import models.User;

import java.util.List;

public class UserService {
    public static boolean checkUserByLoginPass(String login, String pass) {
        UserDBDao userDBDao = new UserDBDao();
        List<User> users = userDBDao.getUserByLoginPass(login, pass);
        if (users.stream().count() == 0){
            return false;
        }else
            return true;
    }

    public static String getUserIdByLogin(String login){
        UserDBDao userDBDao = new UserDBDao();
        User user = userDBDao.getByLogin(login);
        return user.getIdUser();
    }

    public static boolean createUser(String login, String pass, String email) {
        User user = new User(login, pass, email);
        UserDBDao userDBDao = new UserDBDao();
        return userDBDao.add(user);
    }

}
