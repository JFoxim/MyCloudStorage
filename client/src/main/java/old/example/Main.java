package old.example;

import old.example.models.CloudCore;

import java.net.Socket;

/**
 * Created by romanpauesov on 25.02.17.
 */
public class Main {
    public static void main(String[] args){
        Socket socket;
        try
        {
            socket = new Socket("localhost", 9999);
            CloudCore core = new CloudCore(socket);
            ClientFrame frame = new ClientFrame(core);
            frame.setVisible(true);
            System.out.println(core.localFiles.size());
            if (socket.isConnected()) System.out.println("Server is ready");
        } catch (Exception e){
            System.out.println("NO....");
            e.printStackTrace();
        }
    }

}
