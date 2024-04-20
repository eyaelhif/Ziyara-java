package services;

import models.ReservationTransport;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationTService {
    private final Connection connection;

    public ReservationTService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public void create(ReservationTransport reservation) throws SQLException {
        String sql = "INSERT INTO reservation_transport (id,id_user_id, id_transport, date_reservation_transport, point_depart, point_arrive) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, reservation.getIdReservationTransport());
            ps.setInt(2, reservation.getIdUser());
            ps.setInt(3, reservation.getIdTransport());
            ps.setDate(4, new java.sql.Date(reservation.getDateReservationTransport().getTime()));
            ps.setString(5, reservation.getPointDepart());
            ps.setString(6, reservation.getPointArrive());
            ps.executeUpdate();
        }
    }

    public void update(ReservationTransport reservation) throws SQLException {
        String sql = "UPDATE reservation_transport SET id_transport = ?, date_reservation_transport = ?, point_depart = ?, point_arrive = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, reservation.getIdTransport());
            ps.setDate(2, new java.sql.Date(reservation.getDateReservationTransport().getTime()));
            ps.setString(3, reservation.getPointDepart());
            ps.setString(4, reservation.getPointArrive());
            ps.setInt(5, reservation.getIdReservationTransport());
            ps.executeUpdate();
        }
    }

    public void delete(int idReservationTransport) throws SQLException {
        String sql = "DELETE FROM reservation_transport WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idReservationTransport);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No reservation found with id: " + idReservationTransport);
            }
        }
    }

    public List<ReservationTransport> getAllReservations() throws SQLException {
        List<ReservationTransport> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation_transport";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReservationTransport reservation = new ReservationTransport();
                    reservation.setIdReservationTransport(rs.getInt("id"));
                    reservation.setIdUser(rs.getInt("id_user_id"));
                    reservation.setIdTransport(rs.getInt("id_transport"));
                    reservation.setDateReservationTransport(rs.getDate("date_reservation_transport"));
                    reservation.setPointDepart(rs.getString("point_depart"));
                    reservation.setPointArrive(rs.getString("point_arrive"));
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }
}
