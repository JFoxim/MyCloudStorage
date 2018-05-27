import dao.impl.FileDBDao;
import dao.impl.ServerSettingsDBDao;
import dao.impl.UserDBDao;
import models.File;
import models.RightFile;
import models.ServerSettings;
import models.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Ignore
public class FileDaoServerTest {

    @Test
    public void addFileTest() {
        FileDBDao fileDao = new FileDBDao();
        File file = new File("b80de869-53af-4ab5-b490-fe8a9943f178",
                "D:\\DownloadedFies2", "1236");
//        File file = fileDao.getById("b80de869-53af-4ab5-b490-fe8a9943f167");
        Set<User> users = new HashSet<>();
        UserDBDao userDBDao = new UserDBDao();
        users.add(userDBDao.getById("b80de869-53af-4ab5-b490-fe8a9943f167"));
        file.setUser(users);
        Assert.assertTrue(fileDao.add(file, true));
        fileDao.delete(file);
    }

    @Test
    public void getAllFilesTest() {
        FileDBDao fileDBDao  = new FileDBDao();
        List<File> filesList = fileDBDao.getAll();
        Assert.assertTrue(filesList.stream().count()>0);
    }

    @Test
    public void getAllServerSettingsTest() {
        ServerSettingsDBDao serverSettingsDBDao  = new ServerSettingsDBDao();
        List<ServerSettings>  settingsListList = serverSettingsDBDao.getAll();
        Assert.assertTrue(settingsListList.stream().count()>0);
    }

    @Test
    public void getFileByIdTest(){
        FileDBDao fileDBDao  = new FileDBDao();
        Assert.assertEquals("b80de869-53af-4ab5-b490-fe8a9943f167",
                fileDBDao.getById("b80de869-53af-4ab5-b490-fe8a9943f167").getIdFile());
    }
    @Test
    public void getServerSettingsByIdTest(){
        ServerSettingsDBDao serverSettingsDBDao  = new ServerSettingsDBDao();
        Assert.assertEquals(1, serverSettingsDBDao.getById(1).getId());
    }

}
