package services;

import models.ReservationVisite;
import utils.MyDatabase;
import models.IService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationVisiteService implements IService<ReservationVisite> {
    private Connection cnx;
    private PreparedStatement pst;

    public ReservationVisiteService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(ReservationVisite reservationVisite) {
        String query = "INSERT INTO reservation_visite (id_visite_id, id_user_id, date_reservation_visite, nbrparticipant_visite, nom, prenom,numtlph,email) VALUES (?, ?, ?, ?, ?, ?,?,?)";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, reservationVisite.getId_visite());
            pst.setInt(2, reservationVisite.getId_user());
            pst.setString(3, reservationVisite.getDate_reservation_visite());
            pst.setInt(4, reservationVisite.getNbrparticipant_visite());
            pst.setString(5, reservationVisite.getNom());
            pst.setString(6, reservationVisite.getPrenom());
            pst.setString(7, String.valueOf(12345678));
            pst.setString(8, "Example@gmail.com");
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(ReservationVisite reservationVisite) {
        String query = "DELETE FROM reservation_visite WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, reservationVisite.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void deleteById(int reservationId) {
        String query = "DELETE FROM reservation_visite WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, reservationId);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void update(ReservationVisite reservationVisite) {
        String query = "UPDATE reservation_visite SET id_visite_id = ?, id_user_id = ?, date_reservation_visite = ?, nbrparticipant_visite = ?, nom = ?, prenom = ? WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, reservationVisite.getId_visite());
            pst.setInt(2, reservationVisite.getId_user());
            pst.setString(3, reservationVisite.getDate_reservation_visite());
            pst.setInt(4, reservationVisite.getNbrparticipant_visite());
            pst.setString(5, reservationVisite.getNom());
            pst.setString(6, reservationVisite.getPrenom());
            pst.setInt(7, reservationVisite.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<ReservationVisite> readAll() {
        String query = "SELECT * FROM reservation_visite";
        List<ReservationVisite> list = new ArrayList<>();

        try {
            pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ReservationVisite reservationVisite = new ReservationVisite(
                        rs.getInt("id"),
                        rs.getInt("id_visite_id"),
                        rs.getInt("id_user_id"),
                        rs.getString("date_reservation_visite"),
                        rs.getInt("nbrparticipant_visite"),
                        rs.getString("nom"),
                        rs.getString("prenom")
                );
                list.add(reservationVisite);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public ReservationVisite readById(int id) {
        String query = "SELECT * FROM reservation_visite WHERE id = ?";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new ReservationVisite(
                        rs.getInt("id"),
                        rs.getInt("id_visite_id"),
                        rs.getInt("id_user_id"),
                        rs.getString("date_reservation_visite"),
                        rs.getInt("nbrparticipant_visite"),
                        rs.getString("nom"),
                        rs.getString("prenom")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    public List<ReservationVisite> getUserReservations() {
        List<ReservationVisite> allReservations = readAll();
        List<ReservationVisite> userReservations = new ArrayList<>();
        UserService userService = new UserService();
        int userId = userService.getCurrentUserId(); // Get the current user's ID

        // Filter reservations for the current user
        for (ReservationVisite reservation : allReservations) {
            if (reservation.getId_user() == userId) {
                userReservations.add(reservation);
            }
        }

        return userReservations;
    }

    private static final String SELECT_BY_TITRE_QUERY = "SELECT * FROM reservation_visite WHERE id_user_id = ?";

    public List<ReservationVisite> searchByTitre(String titre) {
        List<ReservationVisite> rvisites = new ArrayList<>();
        try (PreparedStatement pst = cnx.prepareStatement(SELECT_BY_TITRE_QUERY)) {
            UserService userService = new UserService();
            pst.setInt(1, userService.userid);




            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int id_visite = resultSet.getInt("id_visite_id");
                    int id_user = resultSet.getInt("id_user_id");
                    String date_reservation_visite = resultSet.getString("date_reservation_visite");
                    int nbrparticipant_visite = resultSet.getInt("nbrparticipant_visite");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    ReservationVisite reservationVisite = new ReservationVisite(id, id_visite,  id_user,  date_reservation_visite,  nbrparticipant_visite,  nom,  prenom);
                    rvisites.add(reservationVisite);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rvisites;
    }
}
