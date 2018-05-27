package Services;

import app.ClientHandler;
import dao.impl.UserDBDao;
import models.User;

import java.util.Vector;

public class UserService {
    public static boolean checkUserByLoginPass(String login, String pass) {
        UserDBDao userDBDao = new UserDBDao();
        User user = userDBDao.getUserByLoginPass(login, pass);
        return user != null ? true : false;
    }

    public static String getUserIdByLogin(String login){
        UserDBDao userDBDao = new UserDBDao();
        User user = userDBDao.getByLogin(login);
        return user.getIdUser();
    }

    public static boolean isBusyLogin(Vector<ClientHandler> clients, String login){
        for (ClientHandler o : clients) {
            if (o.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
