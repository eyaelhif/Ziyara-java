package services;

import models.Visite;
import utils.MyDatabase;
import models.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisiteService implements IService<Visite> {
    private Connection cnx;
    private PreparedStatement pst;

    public VisiteService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Visite visite) {
        String query = "INSERT INTO visite (titre, description_visite, date_visite, prix, imagev) VALUES (?, ?, ?, ?, ?)";

        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, visite.getTitre());
            pst.setString(2, visite.getDescription_visite());
            pst.setString(3, String.valueOf(visite.getDate_visite()));
            pst.setDouble(4, visite.getPrix());
            pst.setString(5, visite.getImagev());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Visite visite) {
        String query = "DELETE FROM visite WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, visite.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Visite visite) {
        String query = "UPDATE visite SET titre = ?, description_visite = ?, date_visite = ?, prix = ?, imagev = ? WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, visite.getTitre());
            pst.setString(2, visite.getDescription_visite());
            pst.setString(3, String.valueOf(visite.getDate_visite()));
            pst.setDouble(4, visite.getPrix());
            pst.setString(5, visite.getImagev());
            pst.setInt(6, visite.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public  List<Visite> readAll() {
        String query = "SELECT * FROM visite";
        List<Visite> list = new ArrayList<>();

        try {
            pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Visite visite = new Visite(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description_visite"),
                        rs.getString("date_visite"),
                        rs.getDouble("prix"),
                        rs.getString("imagev")
                );
                list.add(visite);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Visite> getAllVisite() throws SQLException {
        List<Visite> Visites = new ArrayList<>();
        String sql = "SELECT * FROM Visite";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Visite visite = new Visite();
                    visite.setId(rs.getInt("id"));
                    visite.setTitre(rs.getString("titre"));
                    visite.setDate_visite(String.valueOf(rs.getDate("date_visite")));
                    visite.setPrix(rs.getDouble("prix"));
                    visite.setDescription_visite(rs.getString("description_visite"));
                    visite.setImagev(rs.getString("imagev"));
                    Visites.add(visite);
                }
            }
        }
        return Visites;
    }

    @Override
    public Visite readById(int id) {
        String query = "SELECT * FROM visite WHERE id = ?";

        try {
                pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Visite(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description_visite"),
                        rs.getString("date_visite"),
                        rs.getDouble("prix"),
                        rs.getString("imagev")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Integer> getAllVisiteIds() {
        String query = "SELECT id FROM visite";
        List<Integer> visiteIds = new ArrayList<>();

        try {
            pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                visiteIds.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visiteIds;
    }


    private static final String SELECT_BY_TITRE_QUERY = "SELECT * FROM visite WHERE titre LIKE ?";

    public List<Visite> searchByTitre(String titre) {
        List<Visite> visites = new ArrayList<>();
        try (PreparedStatement pst = cnx.prepareStatement(SELECT_BY_TITRE_QUERY)) {
            pst.setString(1, "%" + titre + "%");
            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String visiteTitre = resultSet.getString("titre");
                    String description = resultSet.getString("description_visite");
                    String dateVisite = resultSet.getString("date_visite");
                    double prix = resultSet.getDouble("prix");
                    String image = resultSet.getString("imagev");
                    Visite visite = new Visite(id, visiteTitre, description, dateVisite, prix, image);
                    visites.add(visite);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visites;
    }

    public List<Visite> searchByTitrefavoris(String titre) {
        List<Visite> visites = new ArrayList<>();
        String query = "SELECT v.* FROM visite v INNER JOIN favoris f ON v.id = f.id_visite_id WHERE v.titre LIKE ?";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, "%" + titre + "%");
            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String visiteTitre = resultSet.getString("titre");
                    String description = resultSet.getString("description_visite");
                    String dateVisite = resultSet.getString("date_visite");
                    double prix = resultSet.getDouble("prix");
                    String image = resultSet.getString("imagev");
                    Visite visite = new Visite(id, visiteTitre, description, dateVisite, prix, image);
                    visites.add(visite);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visites;
    }


}
