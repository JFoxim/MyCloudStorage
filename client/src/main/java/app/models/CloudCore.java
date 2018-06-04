package app.models;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class CloudCore {
    public List<File> localFiles = new ArrayList<File>();
    static Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public CloudCore(Socket socket) throws Exception {
        this.socket = socket;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void getReqest(){
        try {
            output.writeObject("get");
            output.flush();
            Object obj = input.readObject();
            localFiles = (List<File>) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void putFile(File file) {
        localFiles.add(file);
        try {
            output.writeObject(file);
            output.flush();
        } catch (Exception e) {
            System.out.println("Can't send file");
        }
    }

    public void removeFile(File file) {
        localFiles.remove(file);
        try {
            output.writeObject(file);
            output.flush();
        } catch (Exception e) {
            System.out.println("Can't remove file from server");
        }
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }
}


