package services;

import models.CategorieTransport;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieService {
    private static Connection connection ;

        public CategorieService() {
        connection = MyDatabase.getInstance().getConnection();
    }



    public static List<CategorieTransport> readAll() {
        String query = "SELECT * FROM categorie_transport";
        List<CategorieTransport> categories = new ArrayList<>();

        // Use try-with-resources to automatically close the connection
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                CategorieTransport categorie = new CategorieTransport();
                categorie.setIdCategorieTransport(rs.getInt("id"));
                categorie.setNomCategorieTransport(rs.getString("nom_categorie_transport"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }


    public List<Integer> getAllCategorieTransportIds() {
        String query = "SELECT id FROM categorie_transport";
        List<Integer> categorieIds = new ArrayList<>();

        try (Connection connection = MyDatabase.getInstance().getConnection();
             PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                categorieIds.add(rs.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categorieIds;
    }

    public static List<CategorieTransport> getAllCategories() throws SQLException {
        List<CategorieTransport> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie_transport";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CategorieTransport category = new CategorieTransport();
                category.setIdCategorieTransport(rs.getInt("id"));
                category.setNomCategorieTransport(rs.getString("nom_categorie_transport"));
                // Set other properties if needed
                categories.add(category);
            }
        }
        return categories;
    }

}
