package controllers;

import javafx.scene.control.cell.PropertyValueFactory;
import models.Transport;
import services.TransportService;
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

public class ShowTransportController {

    @FXML
    public TableView<Transport> tableView_transport;
    public TableColumn <Transport, String> transportIdColumn;

    @FXML
    private TableColumn<Transport, String> typeColumn;

    @FXML
    private TableColumn<Transport, String> dateColumn;

    @FXML
    private TableColumn<Transport, String> prixColumn;

    @FXML
    private TableColumn<Transport, String> descriptionColumn;

    private final TransportService transportService = new TransportService();

    public void initialize() {
        // Initialize columns and loadData()...
        transportIdColumn.setCellValueFactory(new PropertyValueFactory<>("idTransport"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeTransport"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTransport"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixTransport"));


        loadData();
    }

    public void loadData() {
        try {
            List<Transport> transports = transportService.getAllTransports();
            ObservableList<Transport> transportList = FXCollections.observableArrayList(transports);
            tableView_transport.setItems(transportList);
        } catch (SQLException e) {
            showAlert("Error fetching transports from the database: " + e.getMessage());
        }
    }

    @FXML
    void deleteTransport(ActionEvent event) {
        // Get the selected transport from the table
        Transport selectedTransport = tableView_transport.getSelectionModel().getSelectedItem();
        if (selectedTransport != null) {
            try {
                // Delete the transport from the database
                transportService.delete(selectedTransport.getIdTransport());

                // Remove the transport from the TableView
                tableView_transport.getItems().remove(selectedTransport);

                showAlert("Transport deleted successfully!");
            } catch (SQLException e) {
                showAlert("Failed to delete transport: " + e.getMessage());
            }
        } else {
            showAlert("Please select a transport to delete.");
        }
    }

    @FXML
    public void updateTransport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatetransport.fxml"));
            Parent root = loader.load();
            UpdateTransportController controller = loader.getController();

            // Set the showTransportController reference in the UpdateTransportController
            controller.setShowTransportController(this);

            // Get the selected transport from the table
            Transport selectedTransport = tableView_transport.getSelectionModel().getSelectedItem();
            if (selectedTransport != null) {
                controller.setSelectedTransport(selectedTransport);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Transport");
                stage.show();
            } else {
                showAlert("Please select a transport to update.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addTransport(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addtransport.fxml"));
            Parent root = loader.load();

            // Get the controller of the addTransport.fxml
            AddTransportController addTransportController = loader.getController();

            // Show the add transport window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Transport");
            stage.showAndWait();

            // After the add transport window is closed, refresh the TableView
            loadData(); // Reload the data from the database
        } catch (IOException e) {
            e.printStackTrace(); // Handle any potential errors while loading the FXML file
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
