package controllers;

import javafx.scene.control.cell.PropertyValueFactory;
import models.ReservationTransport;
import services.ReservationTService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowReservationTransportController {
    @FXML
    public TableView<ReservationTransport> tableView_reservationTransport;
    public TableColumn<ReservationTransport, Integer> reservationTransportIdColumn;
    public TableColumn<ReservationTransport, Integer> userIdColumn;
    public TableColumn<ReservationTransport, Integer> transportIdColumn;
    public TableColumn<ReservationTransport, String> dateColumn;
    public TableColumn<ReservationTransport, String> pointDepartColumn;
    public TableColumn<ReservationTransport, String> pointArriveColumn;


    private final ReservationTService reservationTransportService = new ReservationTService();

    public void initialize() {
        reservationTransportIdColumn.setCellValueFactory(new PropertyValueFactory<>("idReservationTransport"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("idUser "));
        transportIdColumn.setCellValueFactory(new PropertyValueFactory<>("idTransport"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservationTransport"));
        pointDepartColumn.setCellValueFactory(new PropertyValueFactory<>("pointDepart"));
        pointArriveColumn.setCellValueFactory(new PropertyValueFactory<>("pointArrive"));

        loadData();
    }

    public void loadData() {
        try {
            List<ReservationTransport> reservationTransports = reservationTransportService.getAllReservations();
            ObservableList<ReservationTransport> reservationTransportList = FXCollections.observableArrayList(reservationTransports);
            tableView_reservationTransport.setItems(reservationTransportList);
        } catch (SQLException e) {
            showAlert("Error fetching reservation transports from the database: " + e.getMessage());
        }
    }

    @FXML
    void deleteReservationTransport(ActionEvent event) {
        ReservationTransport selectedReservationTransport = tableView_reservationTransport.getSelectionModel().getSelectedItem();
        if (selectedReservationTransport != null) {
            try {
                reservationTransportService.delete(selectedReservationTransport.getIdReservationTransport());
                tableView_reservationTransport.getItems().remove(selectedReservationTransport);
                showAlert("Reservation Transport deleted successfully!");
            } catch (SQLException e) {
                showAlert("Failed to delete reservation transport: " + e.getMessage());
            }
        } else {
            showAlert("Please select a reservation transport to delete.");
        }
    }

    @FXML
    public void updateReservationTransport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatereservationtransport.fxml"));
            Parent root = loader.load();
            UpdateReservationTransportController controller = loader.getController();

            controller.setShowReservationTransportController(this);

            ReservationTransport selectedReservationTransport = tableView_reservationTransport.getSelectionModel().getSelectedItem();
            if (selectedReservationTransport != null) {
                controller.setSelectedReservationTransport(selectedReservationTransport);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Reservation Transport");
                stage.show();
            } else {
                showAlert("Please select a reservation transport to update.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addReservationTransport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addRtransport.fxml"));
            Parent root = loader.load();
            addReservationTransportController addReservationTransportController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Reservation Transport");
            stage.showAndWait();
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
