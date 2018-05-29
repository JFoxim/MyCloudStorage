package app;
import Services.FileService;
import Services.UserService;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private ServerCore server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;
    private String id;
    List<File> files = new ArrayList<File>();

    public String getLogin() {
        return nick;
    }

    public String getId() {
        return id;
    }

    public ClientHandler(ServerCore server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/auth ")) {
                            // /auth login1 pass1
                            String[] tokens = msg.split(" ");
                            String login = tokens[1];
                             if (UserService.checkUserByLoginPass(tokens[1], tokens[2])) {
                                if (server.isNickBusy(login)) {
                                    out.writeUTF("Учетная запись уже используется");
                                    continue;
                                }
                                out.writeUTF("/authok " + login);
                                this.nick = login;
                                this.id = UserService.getUserIdByLogin(login);//SQLHandler.getIdByNick(login);
                                server.subscribe(this);
                                 FileService.createUserCloudDirectory(login);
                                break;
                            } else {
                                out.writeUTF("Неверный логин/пароль");
                            }
                        }
                        automaticDisconnect();
                    }
                    while (true) {
                        FilesThread thread = new FilesThread(socket, files);
                        Thread th = new Thread(thread);
                        th.start();
                        String msg = in.readUTF();
                        if (msg.startsWith("/")) {
                            if (msg.startsWith("/w ")) {
                                String[] tokens = msg.split(" ", 3);
                                server.sendPrivateMsg(this, tokens[1], tokens[2]);
                            }
//                            if (msg.equals("/history")) {
//                                sendMsg(SQLHandler.getHistory(id));
//                            }
                        } else {
                            server.broadcastMsg(this, msg);
                        }
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect(){
        server.unsubscribe(this);
        this.nick = null;
        this.id = "-1";
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void automaticDisconnect() {
//        if (nick == null) {
//            Timer timer = new Timer();
//            timer.schedule(new Dis(), 0, 4000);
//            timer.cancel();
//        }
//
//        return
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    Boolean state = false;
                    try {
                        if (nick == null) {
                            String mes = "";
                            for (int i = 120; i >= 1; i--) {
                                mes = i + " секунд до отключения от сервера";
                                Thread.sleep(1000); // 120 секунд в милисекундах
                                sendMsg(mes);
                                System.out.println(mes);
                                if (nick != null){
                                    state = true;
                                    break;
                                }
                            }
                            if (!state)
                                disconnect();
                            break;
                        }
                    } catch (Exception er) {
                    }
                }
            }
        }).start();
    }
}
