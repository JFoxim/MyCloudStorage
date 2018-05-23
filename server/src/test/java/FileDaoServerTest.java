import dao.impl.FileDBDao;
import models.File;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class FileDaoServerTest {

    @Test
    public void addFileTest() {
        FileDBDao fileDao = new FileDBDao();
        File file = new File("b80de869-53af-4ab5-b490-fe8a9943f167",
                "D:\\DownloadedFies", "1236");
        Assert.assertTrue(fileDao.add(file, "b80de869-53af-4ab5-b490-fe8a9943f167"));
    }

    @Test
    public void getByUserIdTest() {
        FileDBDao userFileDao = new FileDBDao();
        List<File> filesList = userFileDao.getByUserId("b80de869-53af-4ab5-b490-fe8a9943f166");
        Assert.assertTrue(filesList.stream().count()>0);

    }
}
