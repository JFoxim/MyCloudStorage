import Services.FileService;
import Services.UserService;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientHandler {

   private Server server;
   private Socket socket;
   private ObjectOutputStream out;
   private ObjectInputStream in;
   private String username;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        Object obj = in.readObject();
                        if (obj instanceof AuthMessage){
                            AuthMessage am = (AuthMessage)obj;
                            if (UserService.checkUserByLoginPass(am.getLogin(), am.getPass())){
                                this.username = am.getLogin();//"client";
                                FileService.createUserCloudDirectory(username);
                                CommandMessage cm = new CommandMessage(CommandMessage.CMD_MSG_AUTH_OK);
                                sendMsg(cm);
                                sendFileList();
                                break;
                            }
                        }
                    }
                    while (true){
                        Object obj = in.readObject();
                        if (obj instanceof AbstractMessage){
                            if (obj instanceof FileMessage){
                                FileMessage fm =(FileMessage) obj;
                                FilePartitionWorker.receiveFile(in, FileService.getGlobalPath() + username, fm, null);
                                sendFileList();
                            }
                        }
                        if (obj instanceof CommandMessage){
                            CommandMessage cm =(CommandMessage)obj;
                            if(cm.getType() == CommandMessage.CMD_MSG_REQUES_FILE_DOWNLOAD){
                                try{
                                    FilePartitionWorker.sendFile(Paths.get(((File)cm.getAttachment()[0]).getAbsolutePath()), out, null);
                                  }catch (Exception e){
                                    e.printStackTrace();
                                }
                                continue;
                            }
                            if (cm.getType() == CommandMessage.CMD_MSG_REQUEST_FILES_LIST){
                                sendFileList();
                            }
                            if(cm.getType() == CommandMessage.CMD_MSG_REQUEST_SERVER_DELETE_FILE){
                               Files.delete(Paths.get(FileService.getGlobalPath() + username +"/"+ cm.getAttachment()[0]));
                               sendFileList();
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                } finally {
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
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(AbstractMessage msg){
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserRootPath(){
        return FileService.getGlobalPath() + username;
        //return "server/repository/" + username;
    }

    public void sendFileList(){
        try {
            FileListMessage flm = new FileListMessage(Paths.get(getUserRootPath()));
            sendMsg(flm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
