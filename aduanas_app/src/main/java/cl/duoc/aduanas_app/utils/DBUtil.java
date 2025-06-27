package cl.duoc.aduanas_app.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream in = DBUtil.class.getResourceAsStream("/db.properties")) {
            Properties props = new Properties();
            props.load(in);
            url      = props.getProperty("db.url");
            user     = props.getProperty("db.user");
            password = props.getProperty("db.password");
            // Opcional: DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception e) {
            throw new ExceptionInInitializerError("No se pudo cargar db.properties: " + e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
