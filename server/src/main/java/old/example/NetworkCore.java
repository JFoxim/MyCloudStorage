package old.example;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by romario on 02.03.17.
 */
public class NetworkCore {
        List<File> files = new ArrayList<File>();

    public NetworkCore(File file) throws Exception{
        File direcory = file;
        ServerSocket server = new ServerSocket(9999);
        while(true) {
            Socket socket = server.accept();
            FilesThread thread = new FilesThread(socket,files);
            Thread th = new Thread(thread);
            th.start();
        }
    }
}
