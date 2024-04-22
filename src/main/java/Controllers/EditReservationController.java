package Controllers;

import entities.Pack;
import entities.ReservationPack;
import entities.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.PackService;
import services.ReservationPackService;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.util.List;

public class EditReservationController {
    public TextField nbparticipants;
    public ComboBox<User> UsersCombobox;
    public ComboBox<Pack> PackComboBox;
    public DatePicker dateRes;
    public ImageView btnReturn;
    public static ReservationPack reservationPack;
    @FXML
    public void initialize() {
        initializeComboBoxes();
        System.out.println(reservationPack.getDateReservationPack().getClass());
        this.dateRes.setValue(reservationPack.getDateReservationPack().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        this.nbparticipants.setText(String.valueOf(reservationPack.getNbrParticipantPack()));
        this.PackComboBox.getSelectionModel().select(reservationPack.getPack());
        this.UsersCombobox.getSelectionModel().select(reservationPack.getUser());
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to Pack CRUD");
                RouterController.navigate("/fxml/ReservationsCrud.fxml");
            }
        });
    }
    ReservationPackService reservationPackService=new ReservationPackService();
    PackService packService=new PackService();
    private void initializeComboBoxes() {
        List<Pack> Packs = packService.getAll();

        List<User> Users = reservationPackService.getAllUsers();
        this.UsersCombobox.getItems().addAll(Users);
        this.PackComboBox.getItems().addAll(Packs);

    }
    public void AddPack(ActionEvent event) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        if (!validateInputsAndProceed()) {
            return;
        }
        ReservationPack newReservationPack=new ReservationPack();
        newReservationPack.setPack(PackComboBox.getSelectionModel().getSelectedItem());
        newReservationPack.setUser(UsersCombobox.getSelectionModel().getSelectedItem());
        newReservationPack.setNbrParticipantPack(Integer.parseInt(nbparticipants.getText()));

        java.util.Date date = java.sql.Date.valueOf(this.dateRes.getValue());
        newReservationPack.setDateReservationPack(date);
        newReservationPack.setId(reservationPack.getId());
        reservationPackService.update(newReservationPack);
        showSuccessMessage("Réservation mis à jour avec succées");
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean validateInputsAndProceed() {
        if (nbparticipants.getText().isEmpty()) {
            showAlert("Nb participants ne peut pas etre vide");
            return false;
        }
        if (Integer.parseInt(nbparticipants.getText()) < 0) {
            showAlert("Nb participants ne peut pas etre négatif");
            return false;
        }
        if (UsersCombobox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Selectionner un utilisateur");
            return false;
        }
        if (PackComboBox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Selectionner un Pack");
            return false;
        }
        return true;

    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void returnTo(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/ReservationsCrud.fxml");
    }
}
