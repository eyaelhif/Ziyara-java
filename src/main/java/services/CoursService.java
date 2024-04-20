package services;

import models.Cours;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements IService<Cours> {

    private Connection connection;

    public CoursService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Cours cours) throws SQLException {
        String sql = "INSERT INTO cours (titre, description, duree, categorie, prix, nbplace, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cours.getTitre());
        ps.setString(2, cours.getDescription());
        ps.setString(3, cours.getDuree());
        ps.setString(4, cours.getCategorie());
        ps.setInt(5, cours.getPrix());
        ps.setInt(6, cours.getNbplace());
        ps.setInt(7, cours.getUser_id());
        ps.executeUpdate();
    }

    @Override
    public void update(Cours cours) throws SQLException {
        String sql = "UPDATE cours SET titre = ?, description = ?, duree = ?, categorie = ?, prix = ?, nbplace = ?, user_id = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cours.getTitre());
        ps.setString(2, cours.getDescription());
        ps.setString(3, cours.getDuree());
        ps.setString(4, cours.getCategorie());
        ps.setInt(5, cours.getPrix());
        ps.setInt(6, cours.getNbplace());
        ps.setInt(7, cours.getUser_id());
        ps.setInt(8, cours.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM cours WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<Cours> read() throws SQLException {
        String sql = "SELECT * FROM cours";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Cours> coursList = new ArrayList<>();
        while (rs.next()) {
            Cours cours = new Cours();
            cours.setTitre(rs.getString("titre"));
            cours.setDescription(rs.getString("description"));
            cours.setDuree(rs.getString("duree"));
            cours.setCategorie(rs.getString("categorie"));
            cours.setPrix(rs.getInt("prix"));
            cours.setNbplace(rs.getInt("nbplace"));
            cours.setUser_id(rs.getInt("user_id"));
            coursList.add(cours);
        }
        return coursList;
    }

    public List<Cours> getAllCourses() throws SQLException {
        List<Cours> coursList = new ArrayList<>();
        String sql = "SELECT * FROM cours";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cours cours = new Cours(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getString("duree"),
                            rs.getString("categorie"),
                            rs.getInt("prix"),
                            rs.getInt("nbplace")
                    );
                    coursList.add(cours);
                }
            }
        }
        return coursList;
    }
}
