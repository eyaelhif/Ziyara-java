package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.ReservationTransport;
import services.ReservationTService;

import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateReservationTransportController {
    @FXML
    private TextField idField;

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


    private ReservationTransport selectedReservationTransport;

    private ShowReservationTransportController showReservationTransportController;

    public void setSelectedReservationTransport(ReservationTransport reservationTransport) {
        this.selectedReservationTransport = reservationTransport;
        idField.setText(String.valueOf(reservationTransport.getIdReservationTransport()));
        userIdField.setText(String.valueOf(reservationTransport.getIdUser()));
        transportIdField.setText(String.valueOf(reservationTransport.getIdTransport()));
        pointDepartField.setText(reservationTransport.getPointDepart());
        pointArriveField.setText(reservationTransport.getPointArrive());

        /*// Convert java.sql.Date to LocalDate for the DatePicker
        if (reservationTransport.getDateReservationTransport() != null) {
            dateField.setValue(reservationTransport.getDateReservationTransport().toLocalDate());
        }*/
    }

    public void setShowReservationTransportController(ShowReservationTransportController showReservationTransportController) {
        this.showReservationTransportController = showReservationTransportController;
    }

    @FXML
    void updateReservationTransport(ActionEvent event) {
        try {
            int userId = Integer.parseInt(userIdField.getText().trim());
            int transportId = Integer.parseInt(transportIdField.getText().trim());
            LocalDate date = dateField.getValue();
            String pointDepart = pointDepartField.getText().trim();
            String pointArrive = pointArriveField.getText().trim();

            // Validate fields
            if (date == null || pointDepart.isEmpty() || pointArrive.isEmpty()) {
                showAlert("Please fill in all required fields!");
                return;
            }

            // Use ReservationTransportService to update the reservation transport in the database
            ReservationTService reservationTransportService = new ReservationTService();
            selectedReservationTransport.setIdUser(userId);
            selectedReservationTransport.setIdTransport(transportId);
            selectedReservationTransport.setDateReservationTransport(java.sql.Date.valueOf(date));
            selectedReservationTransport.setPointDepart(pointDepart);
            selectedReservationTransport.setPointArrive(pointArrive);
            reservationTransportService.update(selectedReservationTransport);

            // Show success message
            showAlert("Reservation Transport updated successfully!");

            // Close the window
            Stage stage = (Stage) idField.getScene().getWindow();
            stage.close();

            // Refresh the table view if showReservationTransportController is not null
            if (showReservationTransportController != null) {
                showReservationTransportController.loadData();
            } else {
                System.out.println("showReservationTransportController is null");
            }
        } catch (SQLException e) {
            showAlert("Failed to update reservation transport: " + e.getMessage());
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
