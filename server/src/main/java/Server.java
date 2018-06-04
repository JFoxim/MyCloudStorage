import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 8189;
    private ServerSocket serverSocket;

    public Server(){
        System.out.println("Сервер запущен");
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Ожидаем подключения");
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
