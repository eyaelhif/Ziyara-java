package controllers;

import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import models.ReservationTransport;
import services.ReservationTService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Date;

public class addReservationTransportController {
    @FXML
    private Button addReservationBtn;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField transportIdField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField pointDepartField;

    @FXML
    private TextField pointArriveField;

    private final ReservationTService reservationTService = new ReservationTService();

    @FXML
    public void addReservation(ActionEvent event) {
        try {
            // Input validation
            int userId = Integer.parseInt(userIdField.getText().trim());
            int transportId = Integer.parseInt(transportIdField.getText().trim());
            Date date = java.sql.Date.valueOf(dateField.getValue());
            String pointDepart = pointDepartField.getText().trim();
            String pointArrive = pointArriveField.getText().trim();

            // Validate fields
            if (pointDepart.isEmpty() || pointArrive.isEmpty()) {
                showAlert("Veuillez remplir tous les champs!");
                return;
            }

            // Create a new ReservationTransport object
            ReservationTransport newReservation = new ReservationTransport();
            newReservation.setIdUser(userId);
            newReservation.setIdTransport(transportId);
            newReservation.setDateReservationTransport(date);
            newReservation.setPointDepart(pointDepart);
            newReservation.setPointArrive(pointArrive);

            // Use ReservationTService to add the new reservation to the database
            reservationTService.create(newReservation);
            showAlert("Reservation added successfully!");

            // Get the stage associated with the addReservationBtn button and close it
            Stage stage = (Stage) addReservationBtn.getScene().getWindow();
            stage.close();

            // You may implement further actions here, like refreshing the view

        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer des valeurs numériques pour les ID!");
        } catch (SQLException e) {
            showAlert("Failed to add reservation: " + e.getMessage());
        }
    }

    // Helper method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
