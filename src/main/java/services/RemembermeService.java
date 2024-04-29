package services;

import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemembermeService {

    private Connection connection;

    public RemembermeService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public int getIdUserFromFirstRecord() {
        try {
            String query = "SELECT iduser FROM rememberme LIMIT 1"; // Change your_table_name accordingly
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("iduser");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
        // If no record found or any error occurs, return -1 or appropriate value as per your logic
        return -1;
    }


    public void setIdUserInFirstRecord(int idUser) {
        try {
            String updateQuery = "UPDATE rememberme SET iduser = ? WHERE id = 1";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setInt(1, idUser);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("ID user set successfully in the first record.");
                } else {
                    System.out.println("No records updated.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }




}
