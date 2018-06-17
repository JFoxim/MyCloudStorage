import server.services.FileService;

import java.util.logging.Level;

public class MainServer {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
          FileService.createGlobalDir();
          new Server();
    }
}
