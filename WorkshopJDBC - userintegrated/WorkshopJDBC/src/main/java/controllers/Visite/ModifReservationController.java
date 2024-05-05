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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.User;
import services.ReservationVisiteService;
import services.UserService;
import services.VisiteService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModifReservationController {
    private ReservationVisiteService reservationVisiteService = new ReservationVisiteService();
    private UserService userService = new UserService();
    private VisiteService visiteService = new VisiteService();
    private ReservationVisite reservationVisite;

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Integer> userIdComboBox;
    @FXML
    private ComboBox<Integer> visiteIdComboBox;
    @FXML
    private TextField nombreParticipantsField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;

    public void initData(ReservationVisite reservationVisite) throws SQLException {
        this.reservationVisite = reservationVisite;

        // Populate user IDs ComboBox
        List<User> users = userService.read();
        List<Integer> userIds = new ArrayList<>();
        for (User user : users) {
            userIds.add(user.getId());
        }
        ObservableList<Integer> userIdsObservable = FXCollections.observableArrayList(userIds);
        userIdComboBox.setItems(userIdsObservable);

        // Populate visit IDs ComboBox
        List<Integer> visiteIds = visiteService.getAllVisiteIds();
        ObservableList<Integer> visiteIdsObservable = FXCollections.observableArrayList(visiteIds);
        visiteIdComboBox.setItems(visiteIdsObservable);

        // Set initial values based on the selected reservation
        datePicker.setValue(LocalDate.parse(reservationVisite.getDate_reservation_visite()));
        nombreParticipantsField.setText(String.valueOf(reservationVisite.getNbrparticipant_visite()));
        nomField.setText(reservationVisite.getNom());
        prenomField.setText(reservationVisite.getPrenom());

        // Select the corresponding user and visit in the ComboBoxes
        userIdComboBox.getSelectionModel().select(reservationVisite.getId_user());
        visiteIdComboBox.getSelectionModel().select(reservationVisite.getId_visite());
    }

    @FXML
    void handleModifReservation(ActionEvent event) {
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

        // Retrieve selected user ID and visit ID from the ComboBoxes
        int selectedUserId = userIdComboBox.getValue();
        int selectedVisiteId = visiteIdComboBox.getValue();

        // Update the reservation
        reservationVisite.setDate_reservation_visite(dateReservation.toString());
        reservationVisite.setNbrparticipant_visite(nombreParticipants);
        reservationVisite.setNom(nom);
        reservationVisite.setPrenom(prenom);
        reservationVisite.setId_user(selectedUserId);
        reservationVisite.setId_visite(selectedVisiteId);

        // Update the reservation using the service
        reservationVisiteService.update(reservationVisite);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

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

    public void returnTo(MouseEvent mouseEvent) {
    }
}
