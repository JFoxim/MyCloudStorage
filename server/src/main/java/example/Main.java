package example;

import java.io.File;

/**
 * Created by romario on 02.03.17.
 */
public class Main {
    public static void main(String[] args){
        NetworkCore core;
        File folder = createCloudDirectory();
        try {
            core = new NetworkCore(folder);
        } catch (Exception e){
            System.out.println("All bad");
            e.printStackTrace();
        }
    }
    public static File createCloudDirectory(){
        File file = new File("D:\\Downloded_files");//"/home/romario/files");
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
