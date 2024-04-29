package Controllers.Guide;

import entities.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceReservation;

import java.sql.SQLException;
import java.time.LocalDate;

public class AjoutReservation {

    @FXML
    private DatePicker dpReservationDate;
    @FXML
    private TextField tfDuration;
    @FXML
    private TextField tfGuideId;
    private int guideId; // This will store the guide ID

    private ServiceReservation serviceReservation = new ServiceReservation(); // Initialize the service

    public void setGuideId(int guideId) {
        this.guideId = guideId; // Method to set the guide ID
    }

    @FXML
    private void submitReservation() {
        try {
            LocalDate reservationDate = dpReservationDate.getValue();
            LocalDate today = LocalDate.now();

            // Vérifier si la date de réservation est dans le passé
            if (reservationDate.isBefore(today)) {
                showAlert("Input Error", "The reservation date cannot be in the past.", Alert.AlertType.ERROR);
                return; // Stop further execution
            }

            int duration = Integer.parseInt(tfDuration.getText());
            Reservation reservation = new Reservation(0, this.guideId, reservationDate, duration);

            // Utilisez le service pour enregistrer la réservation
            serviceReservation.saveReservation(reservation);

            showAlert("Reservation Successful", "Your reservation has been saved.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Error", "Failed to save the reservation: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please check your input values", Alert.AlertType.ERROR);
        }
    }



    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
