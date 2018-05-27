package app;

import Services.FileService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerCore {
    private ServerSocket serverSocket;
    private Vector<ClientHandler> clients;

    public ServerCore() {
        try {
            FileService.createGlobalDir();
            serverSocket = new ServerSocket(9999);
            clients = new Vector<ClientHandler>();
            System.out.println("Сервер запущен");
            while (true) {
                Socket socket = serverSocket.accept();
                 System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendPrivateMsg(ClientHandler from, String to, String msg) {
        for (ClientHandler o : clients) {
            if(o.getLogin().equals(to)) {
                o.sendMsg("from " + from.getLogin() + ": " + msg);
                from.sendMsg("to " + to + ": " + msg);
                //SQLHandler.addHistory(from.getId(), o.getId(), "from " + from.getLogin() + ": " + msg);
                //SQLHandler.addHistory(from.getId(), from.getId(), "to " + o.getLogin() + ": " + msg);
                return;
            }
        }
        from.sendMsg("Клиент " + to + " отсутствует");
    }

    public void broadcastMsg(ClientHandler client, String msg) {
        String outMsg = client.getLogin() + ": " + msg;
        //SQLHandler.addHistory(client.getId(), -1, outMsg);
        for (ClientHandler o : clients) {
            o.sendMsg(outMsg);
        }
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientslist ");
        for (ClientHandler o : clients) {
            sb.append(o.getLogin() + " ");
        }
        String out = sb.substring(0, sb.length() - 1);
        for (ClientHandler o : clients) {
            o.sendMsg(out);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientsList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientsList();
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getLogin().equals(nick)) {
                return true;
            }
        }
        return false;
    }

}
