package services;

import Entities.User;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Connection cnx;
    private PreparedStatement pst;

    public UserService() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void add(User user) {
        String query = "INSERT INTO user (email, roles) VALUES (?, ?)";

        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getRoles());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(User user) {
        String query = "DELETE FROM user WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, user.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(User user) {
        String query = "UPDATE user SET email = ?, roles = ? WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getRoles());
            pst.setInt(3, user.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<User> readAll() {
        String query = "SELECT * FROM user";
        List<User> list = new ArrayList<>();

        try {
            pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("roles")
                );
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public User readById(int id) {
        String query = "SELECT * FROM user WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("roles")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


        // Method to retrieve all user IDs from the database
        public List<Integer> getAllUserIds() {
            String query = "SELECT id FROM user";
            List<Integer> userIds = new ArrayList<>();

            try {
                pst = cnx.prepareStatement(query);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    userIds.add(rs.getInt("id"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return userIds;
        }
}
