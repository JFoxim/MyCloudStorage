package util;

import models.File;
import models.RightFile;
import models.ServerSettings;
import models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(File.class)
                    .addAnnotatedClass(RightFile.class)
                    .addAnnotatedClass(RightFile.UserFile.class)
                    .addAnnotatedClass(ServerSettings.class)
                    .configure().buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}