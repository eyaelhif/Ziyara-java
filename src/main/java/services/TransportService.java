package services;

import models.CategorieTransport;
import models.Transport;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransportService {
    private  Connection connection;

    public TransportService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public void create(Transport transport) throws SQLException {
        String sql = "INSERT INTO transport (date_transport, prix_transport, description, image_transport, type_transport_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(transport.getDateTransport().getTime()));
            ps.setDouble(2, transport.getPrixTransport());
            ps.setString(3, transport.getDescription());
            ps.setString(4, transport.getImageTransport());
            ps.setInt(5, transport.getTypeTransport().getIdCategorieTransport()); // Assuming getId() returns the ID of the CategorieTransport
            ps.executeUpdate();
        }
    }




    public void update(Transport transport) throws SQLException {
        String sql = "UPDATE transport SET date_transport = ?, prix_transport = ?, description = ?, image_transport = ?, type_transport_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(transport.getDateTransport().getTime()));
            ps.setDouble(2, transport.getPrixTransport());
            ps.setString(3, transport.getDescription());
            ps.setString(4, transport.getImageTransport());
            ps.setInt(5, transport.getTypeTransport().getIdCategorieTransport()); // Assuming getId() returns the ID of the CategorieTransport
            ps.setInt(6, transport.getIdTransport());
            ps.executeUpdate();
        }
    }



    public void delete(int idTransport) throws SQLException {
        String sql = "DELETE FROM transport WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTransport);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No transport found with id: " + idTransport);
            }
        }
    }

    public List<Transport> getAllTransports() throws SQLException {
        List<Transport> transports = new ArrayList<>();
        String sql = "SELECT * FROM transport";
        // Use try-with-resources to automatically close the connection
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Transport transport = new Transport();
                transport.setIdTransport(rs.getInt("id"));
                transport.setDateTransport(rs.getDate("date_transport"));
                transport.setPrixTransport(rs.getDouble("prix_transport"));
                transport.setDescription(rs.getString("description"));
                transport.setImageTransport(rs.getString("image_transport"));
                transports.add(transport);
            }
        }
        return transports;
    }

}
