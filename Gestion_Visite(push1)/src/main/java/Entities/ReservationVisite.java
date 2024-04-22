package Entities;

import java.sql.*;
import java.time.LocalDate;

public class ReservationVisite {
    private int id;
    private int id_visite;
    private int id_user;
    private String date_reservation_visite;
    private int nbrparticipant_visite;
    private String nom;
    private String prenom;

    public ReservationVisite() {}

    public ReservationVisite(int id_visite, int id_user, String date_reservation_visite, int nbrparticipant_visite, String nom, String prenom) {
        this.id_visite = id_visite;
        this.id_user = id_user;
        this.date_reservation_visite = date_reservation_visite;
        this.nbrparticipant_visite = nbrparticipant_visite;
        this.nom = nom;
        this.prenom = prenom;
    }

    public ReservationVisite(int id, int id_visite, int id_user, String date_reservation_visite, int nbrparticipant_visite, String nom, String prenom) {
        this.id = id;
        this.id_visite = id_visite;
        this.id_user = id_user;
        this.date_reservation_visite = date_reservation_visite;
        this.nbrparticipant_visite = nbrparticipant_visite;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_visite() {
        return id_visite;
    }

    public void setId_visite(int id_visite) {
        this.id_visite = id_visite;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDate_reservation_visite() {
        return date_reservation_visite;
    }

    public void setDate_reservation_visite(String date_reservation_visite) {
        this.date_reservation_visite = date_reservation_visite;
    }

    public int getNbrparticipant_visite() {
        return nbrparticipant_visite;
    }

    public void setNbrparticipant_visite(int nbrparticipant_visite) {
        this.nbrparticipant_visite = nbrparticipant_visite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "ReservationVisite{" +
                "id=" + id +
                ", id_visite=" + id_visite +
                ", id_user=" + id_user +
                ", date_reservation_visite='" + date_reservation_visite + '\'' +
                ", nbrparticipant_visite=" + nbrparticipant_visite +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    public Visite getVisiteById(int visiteId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Visite visite = null;

        try {
            // Establish connection (replace "url", "username", and "password" with your actual database connection details)
            connection = DriverManager.getConnection("url", "username", "password");

            // Prepare SQL statement
            String sql = "SELECT * FROM Visite WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);

            // Set the visiteId parameter
            preparedStatement.setInt(1, visiteId);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                // Retrieve visite details from the result set
                String titre = resultSet.getString("titre");
                String description = resultSet.getString("description_visite");
                String dateVisite = String.valueOf(resultSet.getDate("date_visite")); // Assuming date_visite is stored as a DATE in the database
                double prix = resultSet.getDouble("prix");
                String imagev = resultSet.getString("imagev");

                // Create Visite object
                visite = new Visite(visiteId, titre, description, dateVisite, prix, imagev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return visite;
    }
}
