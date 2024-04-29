package services;

import entities.Reservation;
import utils.MyDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation {

    private Connection con;

    public ServiceReservation() {
        con = MyDB.getInstance().getConnection();
    }
    public void saveReservation(Reservation reservation) throws SQLException {
        if (reservation.getId() == 0) {
            ajouter(reservation);
        } else {
            modifier(reservation);
        }
    }
    public void ajouter(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservation (guide_id, date_reservation_guide, duree) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, reservation.getGuideId());
        pst.setDate(2, java.sql.Date.valueOf(reservation.getDateReservationGuide()));
        pst.setInt(3, reservation.getDuree());
        pst.executeUpdate();
    }

    public void modifier(Reservation reservation) throws SQLException {
        String query = "UPDATE reservation SET guide_id=?, date_reservation_guide=?, duree=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, reservation.getGuideId());
        pst.setDate(2, java.sql.Date.valueOf(reservation.getDateReservationGuide()));
        pst.setInt(3, reservation.getDuree());
        pst.setInt(4, reservation.getId());
        pst.executeUpdate();
    }

    public void supprimer(Reservation reservation) throws SQLException {
        String query = "DELETE FROM reservation WHERE id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, reservation.getId());
        pst.executeUpdate();
    }


    public List<Reservation> afficher() throws SQLException {
        List<Reservation> listReservation = new ArrayList<>();
        String req = "SELECT * FROM Reservation";
        Statement ste = con.createStatement();
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {
            int id = rs.getInt("id");
            int guideId = rs.getInt("guide_id");
            LocalDate dateReservation = rs.getDate("date_reservation_guide").toLocalDate();
            int duree = rs.getInt("duree");

            Reservation reservation = new Reservation(id, guideId, dateReservation, duree);
            listReservation.add(reservation);
        }
        return listReservation;
    }
    public List<Reservation> getReservationsByGuide(int guideId) throws SQLException {
        List<Reservation> listreservation = new ArrayList<>();
        String req = "SELECT * FROM reservation WHERE reservation_id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, guideId);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            int guideIdFromDB = res.getInt("guide_id");  // Assuming column name is 'guide_id'
            LocalDate dateReservationGuide = res.getDate("date_reservation_guide").toLocalDate();  // Ensure correct column name and conversion
            int duree = res.getInt("duree");

            Reservation reservation = new Reservation(id, guideIdFromDB, dateReservationGuide, duree);
            listreservation.add(reservation);
        }
        return listreservation;

    }
}
