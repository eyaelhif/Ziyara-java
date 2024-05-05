package controllers.Visite;

import models.ReservationVisite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import services.ReservationVisiteService;

import java.io.IOException;
import java.sql.SQLException;

public class ReservationListController {

    private ReservationVisiteService reservationService = new ReservationVisiteService();

    @FXML
    private TableView<ReservationVisite> reservationTableView;

    @FXML
    private TableColumn<ReservationVisite, String> visiteIdColumn;

    @FXML
    private TableColumn<ReservationVisite, String> userIdColumn;

    @FXML
    private TableColumn<ReservationVisite, String> dateReservationColumn;

    @FXML
    private TableColumn<ReservationVisite, String> nomColumn;

    @FXML
    private TableColumn<ReservationVisite, String> prenomColumn;

    @FXML
    private TableColumn<ReservationVisite, Integer> nombreParticipantsColumn;

    @FXML
    private Label titleLabel;

    @FXML
    private Button modifierButton;

    @FXML
    private Button supprimerButton;

    @FXML
    void initialize() {
        try {

            ObservableList<ReservationVisite> observableList = FXCollections.observableList(reservationService.readAll());
            reservationTableView.setItems(observableList);
            visiteIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_visite"));
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            dateReservationColumn.setCellValueFactory(new PropertyValueFactory<>("date_reservation_visite"));
            nombreParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("nbrparticipant_visite"));
            nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void addReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/AjoutReservationVisiteFXML.fxml"));
            Parent root = loader.load();
            AddReservationVisiteController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierReservation(ActionEvent event) {
        ReservationVisite selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ModifReservationVisiteFXML.fxml"));
                Parent root = loader.load();

                // Pass the selected reservation details to the controller of the modifReservationFXML scene
                ModifReservationController modifReservationController = loader.getController();
                modifReservationController.initData(selectedReservation);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert(AlertType.WARNING, "Aucune réservation sélectionnée", "Veuillez sélectionner une réservation à modifier.");
        }
    }

    @FXML
    void supprimerReservation(ActionEvent event) {
        ReservationVisite selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            reservationService.delete(selectedReservation);
            refreshTableView();
        } else {
            showAlert(AlertType.WARNING, "Aucune réservation sélectionnée", "Veuillez sélectionner une réservation à supprimer.");
        }
    }

    private void refreshTableView() {
        ObservableList<ReservationVisite> observableList = FXCollections.observableList(reservationService.readAll());
        reservationTableView.setItems(observableList);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void handleVisitesButtonClicked(ActionEvent event) {
        try {
            // Load the Visite list FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeVisiteFXML.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeReservationVisiteFXML.fxml"));
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

    public void searchquery(KeyEvent keyEvent) {
    }

    public void goToNavigate(ActionEvent actionEvent) {
    }
}
