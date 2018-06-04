package Services;

import dao.UserDao;
import dao.impl.UserDBDao;

import java.io.File;

public class FileService {

    private static String globalPath;
    private static String getUserPath;

    public static String getGlobalPath(){
        return globalPath;
    }

    public static File createGlobalDir(){

        SettingsService settingsService = new SettingsService();
        globalPath = settingsService.getPath();

        File globalDir = new File(globalPath);
        if (!globalDir.exists()){
            try {
                globalDir.mkdir();
            } catch (Exception e){
                System.out.println("ошибка создания глобальной директории");
                return null;
            }
        }
        System.out.println("Директория готова");
        return globalDir.exists()? globalDir : null;
    }

    public static boolean addFileGlobalDir(String fileName){
        boolean result = false;
        String userFilePath = String.format("%s%s%s", globalPath, File.separator, getUserPath);
        File file = new File(userFilePath);
        return result;
    }

    public static boolean deleteFileGlobalDir(String fileName){
        boolean result = false;
        String userFilePath = String.format("%s%s%s", globalPath, File.separator, getUserPath);
        try {
            File file = new File(userFilePath);
            file.delete();
        }
        catch (Exception er){
            System.out.println(er.getMessage());
        }
        return result;
    }

    public static File createUserCloudDirectory(String login){
        String userPath = String.format("%s%s%s", globalPath, File.separator, login);
        File file = new File(userPath);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e){
                System.out.println("Директория не поднялась");
            }
        } else {
            System.out.println("Директория пользователя готова");
        }
        getUserPath = userPath;
        return file.exists()? file : null;
    }
}
