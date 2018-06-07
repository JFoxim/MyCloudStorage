import dao.impl.UserDBDao;
import models.File;
import models.User;
import org.junit.*;

import java.util.List;
import java.util.Set;


@Ignore
public class UserDaoServerTest {

//    @Before
//    public void initData(){
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.close();
//        UserDBDao userDao = new UserDBDao();
//        User user = new User("b80de869-53af-4ab5-b490-fe8a9943f166",
//                "testUser", "123", "testUser@mail.ru");
//        User user3 = new User("b80de869-53af-4ab5-b490-fe8a9943f168",
//                "testUser3", "123", "testUser3@mail.ru");
//        userDao.add(user);
//        userDao.add(user3);
//    }

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
        User user = null;
        try {
            user = new User("b80de869-53af-4ab5-b590-fe8a9943f167",
                    "user3", EncriptService.run("123"), "user3@mail.ru");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        List<User> user = userDBDao.getUserByLoginPass("testUser", "123");
        Assert.assertEquals("testUser@mail.ru", user.get(0).getEmail());
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

    @Test
    public void getUserFiles(){
        UserDBDao userDBDao = new UserDBDao();
        Set<File> files = userDBDao.getById("b80de869-53af-4ab5-b490-fe8a9943f166").getFiles();
        Assert.assertEquals(1, userDBDao.getById("b80de869-53af-4ab5-b490-fe8a9943f166").getFiles().stream().count());
    }
}
