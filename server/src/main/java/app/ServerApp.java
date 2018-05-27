package app;

import java.util.logging.Level;

public class ServerApp {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        new ServerCore();
    }
}

