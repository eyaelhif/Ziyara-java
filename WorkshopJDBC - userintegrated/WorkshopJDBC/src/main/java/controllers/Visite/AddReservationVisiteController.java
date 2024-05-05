package controllers.Visite;

import models.ReservationVisite;
import models.User;
import models.Visite;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import services.ReservationVisiteService;
import services.UserService;
import services.VisiteService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddReservationVisiteController {
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


    @FXML
    public void initialize() throws SQLException {
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
    }
    @FXML
    void handleAddReservationVisite(ActionEvent event) throws SQLException {
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
        List<User> users = userService.read();
        List<Visite> visites = visiteService.readAll();

        if (users.isEmpty() || visites.isEmpty()) {
            showAlert("Aucun utilisateur ou visite disponible dans la base de données.");
            return;
        }

        // For simplicity, just select the first user and visit for now
        int userId = users.get(0).getId();
        int visiteId = visites.get(0).getId();

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

        Notifications.create()
                .title("Ziyara")
                .text("Réservation ajoutée avec succès !")
                .showWarning();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeReservationVisiteFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage (optional)
            ((Stage) datePicker.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

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
