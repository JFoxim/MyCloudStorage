package example.models;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by romario on 16.03.17.
 */
public class CloudCore {
    public List<File> localFiles = new ArrayList<File>();
    static Socket socket;
    ObjectOutputStream output;
    ObjectInputStream input;

    public CloudCore(Socket socket) throws Exception{
        this.socket = socket;
        output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject("get");
        output.flush();
        input = new ObjectInputStream(socket.getInputStream());
        Object obj = input.readObject();
        localFiles = (List<File>)obj;
    }
    public void putFile(File file){
        localFiles.add(file);
        try {
            output.writeObject(file);
            output.flush();
        } catch (Exception e){
            System.out.println("Can't send file");
        }
    }
    public void removeFile(File file){
        localFiles.remove(file);
        try{
            output.writeObject(file);
            output.flush();
        } catch (Exception e){
            System.out.println("Can't remove file from server");
        }
    }

}
