package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class NetCore {

    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Socket socket;

    public NetCore(){ }

    public void initSocket() {
        try {
            socket = new Socket("localhost", 8189);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObjectInputStream getIn() {
        return in;
    }

    public static ObjectOutputStream getOut() {
        return out;
    }

    public static Socket getSocket(){
        return socket;
    }
}
