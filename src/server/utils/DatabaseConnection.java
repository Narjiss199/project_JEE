package server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String USER = "root"; // Modifier avec votre utilisateur MySQL
    private static final String PASSWORD = "123"; // Modifier avec votre mot de passe MySQL

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver non trouvé", e);
        }
    }
}