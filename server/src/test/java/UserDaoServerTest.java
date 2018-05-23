import dao.impl.UserDBDao;
import models.User;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.HibernateUtil;

import java.util.List;


public class UserDaoServerTest {

    @Before
    public void initData(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
        UserDBDao userDao = new UserDBDao();
        User user = new User("b80de869-53af-4ab5-b490-fe8a9943f166",
                "testUser", "123", "testUser@mail.ru");
        User user3 = new User("b80de869-53af-4ab5-b490-fe8a9943f168",
                "testUser3", "123", "testUser3@mail.ru");
        userDao.add(user);
        userDao.add(user3);
    }

//    @After
//    public void clearData(){
//        UserDBDao userDao = new UserDBDao();
//        User user = new User("b80de869-53af-4ab5-b490-fe8a9943f166",
//                "testUser", "123", "testUser@mail.ru");
//        User user2 = new User("b80de869-53af-4ab5-b490-fe8a9943f167",
//                "testUser2", "1236", "testUser2@mail.ru");
//        User user3 = new User("b80de869-53af-4ab5-b490-fe8a9943f168",
//                "testUser3", "123", "testUser3@mail.ru");
//        userDao.delete(user);
//        userDao.delete(user2);
//        userDao.delete(user3);
//    }

    @Test
    public void addUserTest(){
        UserDBDao userDao = new UserDBDao();
        User user = new User("b80de869-53af-4ab5-b490-fe8a9943f167",
                "testUser2", "1236", "testUser2@mail.ru");
        Assert.assertTrue(userDao.add(user));
    }

    @Test
    public void getUserByIdTest(){
        UserDBDao userDao = new UserDBDao();
        Assert.assertEquals("testUser", userDao.getById("b80de869-53af-4ab5-b490-fe8a9943f166").getLogin());
    }

    @Test
    public void getAllUserTest(){
        UserDBDao userDao = new UserDBDao();
        Assert.assertTrue(userDao.getAll().stream().count() > 0);
    }

    @Test
    public void getUserByLoginPassTest(){
        UserDBDao userDBDao = new UserDBDao();
        User user = userDBDao.getUserByLoginPass("testUser", "123");
        Assert.assertEquals("testUser@mail.ru", user.getEmail());
    }

    @Test
    public void updateUserTest(){
        UserDBDao userDBDao = new UserDBDao();
        User user = new User("b80de869-53af-4ab5-b490-fe8a9943f168",
                "testUser3", "123", "testUser3@mail.ru");
        user.setLogin("testUser3_updated");
        Assert.assertTrue(userDBDao.update(user));
        Assert.assertEquals(user.getLogin(), userDBDao.getById("b80de869-53af-4ab5-b490-fe8a9943f168").getLogin());
    }
}
