package Controllers.Guide;

import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import services.ServiceReservation;

import java.sql.SQLException;
import java.util.List;

public class AfficherReservationsController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colGuideId;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    private VBox root;
    private ServiceReservation serviceReservation = new ServiceReservation();

    @FXML
    public void initialize() {
        // Setup columns
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGuideId.setCellValueFactory(new PropertyValueFactory<>("guideId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateReservationGuide"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duree"));

        // Load data
        loadReservationsData();
    }

    private void loadReservationsData() {
        try {
            // Fetch the list of reservations from the database through the service
            List <Reservation> reservations = serviceReservation.afficher();
            // Convert the list of reservations into an ObservableList for TableView compatibility
            ObservableList<Reservation> reservationData = FXCollections.observableArrayList(reservations);
            // Set the TableView to display the fetched reservations
            reservationsTable.setItems(reservationData);
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace to help with debugging
            // Optionally show an error message to the user or log the error more formally
            System.err.println("Failed to load reservations from database: " + e.getMessage());
        }
    }



}
