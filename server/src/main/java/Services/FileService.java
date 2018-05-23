package Services;

import dao.UserDao;
import dao.impl.UserDBDao;

import java.io.File;

public class FileService {

    public static String globalPath="D:\\Downloded_files";

    public static File createGlobalDir(){
        File globalDir = new File(globalPath);
        if (!globalDir.exists()){
            try {
                globalDir.mkdir();
            } catch (Exception e){
                System.out.println("ошибка создания глобальной директории");
                return null;
            }
        }
        return globalDir.exists()? globalDir : null;
    }

    public static File createCloudDirectory(String userId){

        File file = new File(String.format("%s%s%s", globalPath, File.separator, userId));//"/home/romario/files");
        if (!file.exists()) {
            try {
                file.mkdir();
                System.out.println("Директория создана");
            } catch (Exception e){
                System.out.println("Директория не поднялась");
            }
        } else {
            System.out.println("Директория готова");
        }
        return file.exists()? file : null;
    }
}
