package controllers;

import Entities.ReservationVisite;
import Entities.User;
import Entities.Visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ReservationVisiteService;
import services.UserService;
import services.VisiteService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddReservationUserController {
    private ReservationVisiteService reservationVisiteService = new ReservationVisiteService();
    private UserService userService = new UserService();
    private VisiteService visiteService = new VisiteService();

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox userIdComboBox;
    @FXML
    private ComboBox visiteIdComboBox;
    @FXML
    private TextField nombreParticipantsField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;
    private int vid;


    @FXML
    public void initialize() {
    }
    @FXML
    void handleAddReservationVisite(ActionEvent event) {
        LocalDate dateReservation = datePicker.getValue();
        String nombreParticipantsText = nombreParticipantsField.getText().trim();
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();

        // Perform validation checks
        // Check if all fields are filled
        if (dateReservation == null || nombreParticipantsText.isEmpty() || nom.isEmpty() || prenom.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Validate nombreParticipants
        int nombreParticipants;
        try {
            nombreParticipants = Integer.parseInt(nombreParticipantsText);
            if (nombreParticipants <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert("Le nombre de participants doit être un entier positif.");
            return;
        }

        // Retrieve user ID and visit ID from the database
        List<User> users = userService.readAll();




        // For simplicity, just select the first user and visit for now
        int userId = users.get(0).getId();
        int visiteId = vid;

        // Create a new ReservationVisite object
        ReservationVisite reservationVisite = new ReservationVisite();
        reservationVisite.setId_user(userId);
        reservationVisite.setId_visite(visiteId);
        reservationVisite.setDate_reservation_visite(dateReservation.toString());
        reservationVisite.setNbrparticipant_visite(nombreParticipants);
        reservationVisite.setNom(nom);
        reservationVisite.setPrenom(prenom);

        // Add the reservation using the service
        reservationVisiteService.add(reservationVisite);

        // Show success message
        showAlert("Réservation ajoutée avec succès !");
        clearForm();
    }

    private void clearForm() {
        datePicker.setValue(null);
        nombreParticipantsField.clear();
        nomField.clear();
        prenomField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Système de gestion des réservations de visite");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleVisitesButtonClicked(ActionEvent event) {
        try {
            // Load the Visite list FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeVisiteFXML.fxml"));
            Parent root = loader.load();
            // Display the Visite list scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleReservationVisitesButtonClicked(ActionEvent event) {
        try {
            // Load the Reservation-Visite list FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeReservationVisiteFXML.fxml"));
            Parent root = loader.load();
            // Display the Reservation-Visite list scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVisiteId(int id) {
        this.vid=id;
    }
}
