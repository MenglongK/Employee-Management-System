package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static Connection connection;

    // get connection
    public static Connection getConnection() {
        return connection;
    }

    // initialize connection at the first time
    public static void init() {
        if (connection == null) {

            // step 1: load drive on PostgreSQL
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // step 2: define connection URL
            String url = "jdbc:postgresql://localhost:5432/db_employee";
            Properties info = new Properties();
            info.put("user", "menglongkeo");
            info.put("password", "menglong");

            // step 3: establish connection
            try {
                connection = DriverManager.getConnection(url, info);
                System.out.println(connection.getSchema());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
