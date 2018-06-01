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
    private OutputStream outStrem;
    private InputStream inStream;

    public CloudCore(Socket socket) throws Exception{
        this.socket = socket;
        this.output = output;
        output.writeObject("get");
        output.flush();
        this.input = input;
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

    public ObjectOutputStream getOutput(){
        return output;
    }

    public ObjectInputStream getInput(){
        return input;
    }

    public void setOutput(ObjectOutputStream output){
        this.output = output;
    }

    public void setInput(ObjectInputStream input){
        this.input = input;
    }

    public OutputStream getOutStrem() {
        return outStrem;
    }

    public void setOutStrem(OutputStream outStrem) {
        this.outStrem = outStrem;
    }

    public InputStream getInStream() {
        return inStream;
    }

    public void setInStream(InputStream inStream) {
        this.inStream = inStream;
    }
}
