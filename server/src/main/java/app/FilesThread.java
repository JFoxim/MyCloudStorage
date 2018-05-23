package app;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by romanpauesov on 22.03.17.
 */
public class FilesThread implements Runnable {
    public Socket socket;
    public List<File> files;
    public FilesThread(Socket socket, List<File> files){
        this.socket = socket;
        this.files = files;
    }


    public void run() {
        InputStream in;
        OutputStream os;
        try {
            in = socket.getInputStream();
            os = socket.getOutputStream();
            ObjectInputStream inputObject = new ObjectInputStream(in);
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            while(true){
                Object request = new Object();
                while (true){
                    request = inputObject.readObject();
                    if (request instanceof File){
                        File requestFile = (File) request;
                        System.out.println("Получен файл "+requestFile.getName());
                        if (files.contains(requestFile)){
                            files.remove(requestFile);
                            System.out.println("Количество объектов после удаления "+files.size());
                        }
                        else {
                            files.add(requestFile);
                            System.out.println("Количество объектов после добавления "+files.size());
                        }
                    }
                    if (request instanceof String){
                        String question = request.toString();
                        if (question.equals("get")){
                            System.out.println(" Получена команда get");
                            outputStream.writeObject(files);
                            outputStream.flush();
                            System.out.println("Файлы отправлены клиенту!");
                        }
                    }
                }



            }

        } catch (Exception e){

        }
    }
}
