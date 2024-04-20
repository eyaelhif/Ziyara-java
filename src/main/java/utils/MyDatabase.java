package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/integration?autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
    private static final String USER = "root";
    private static final String PASS = "";
    private Connection connection;

    private static MyDatabase instance;

    private MyDatabase() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection established");
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions properly
            System.err.println("Error connecting to the database: " + e.getMessage());
            // You might want to throw an exception here or handle it differently based on your application's requirements
        }
    }

    public static MyDatabase getInstance() {
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
