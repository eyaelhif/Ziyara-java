package controllers.Visite;

import models.Visite;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Notifications;
import services.VisiteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AddVisiteController {
    VisiteService visiteService = new VisiteService();

    @FXML
    private TextField descriptionArea;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField visitNameField;

    @FXML
    private TextField priceField;

    @FXML
    private ImageView imageView;
    private String imageURL;

    @FXML
    void importerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            imageURL = selectedFile.getAbsolutePath();
            Image image = new Image("file:" + selectedFile.getAbsolutePath());
            imageView.setImage(image);
        }
    }

    @FXML
    void handleAddVisite(ActionEvent event) {

        String titre = visitNameField.getText().trim();
        String description = descriptionArea.getText().trim();
        String prixText = priceField.getText().trim();

        // Check if all fields are filled
        if (titre.isEmpty() || description.isEmpty() || prixText.isEmpty() || datePicker.getValue() == null || imageView.getImage() == null) {
            showAlert("Veuillez remplir tous les champs et importer une image.");
            return;
        }
        if (!titre.matches("[a-zA-Z ]+")) {
            showAlert("Le titre ne doit contenir que des lettres et des espaces.");
            return;
        }

        if (!description.matches("[a-zA-Z ]+")) {
            showAlert("La description ne doit contenir que des lettres et des espaces.");
            return;
        }



        // Parse prix to ensure it's a valid number and positive
        double prix;
        try {
            prix = Double.parseDouble(prixText);
            if (prix <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert("Le prix doit être un nombre positif valide.");
            return;
        }

        // Perform image upload validation here (e.g., check if an image is uploaded)

        // Create a new Visite object
        Visite visite = new Visite();
        visite.setTitre(titre);
        visite.setDescription_visite(description);
        visite.setDate_visite(datePicker.getValue().toString());
        visite.setPrix(prix);

        // Set the image URL from the member variable
        visite.setImagev(imageURL);

        // Add the visite using the service
        visiteService.add(visite);

        // Show success message

             Notifications.create()
                            .title("Ziyara")
                             .text("Visite Ajouté Avec Sucessé")
                             .showWarning();

        clearForm();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Visite/ListeVisiteFXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void clearForm() {
        visitNameField.clear();
        descriptionArea.clear();
        priceField.clear();
        datePicker.setValue(null);
        imageView.setImage(null);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Système de gestion des visites");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void importImage(ActionEvent actionEvent) {
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
