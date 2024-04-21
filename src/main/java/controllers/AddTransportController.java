package controllers;

import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Transport;
import services.TransportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AddTransportController {

    @FXML
    private Button addTransportBtn;

    @FXML
    private TextField typeField;

    @FXML
    private DatePicker dateField;


    @FXML
    private TextField prixField;

    @FXML
    private TextField descriptionField;

    private final TransportService transportService = new TransportService();

    @FXML
    private Button imagePathField;
    @FXML
    private ImageView imageView;
    @FXML
    public void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            imagePathField.setText(imagePath);

            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
        } else {
            showAlert("Veuillez sélectionner une image !");
        }
    }

    @FXML
    public void addTransport(ActionEvent event) {
        // Check if an image has been selected
        if (imagePathField.getText().isEmpty()) {
            showAlert("Veuillez sélectionner une image !");
            return;
        }

        // Continue with transport addition logic
        String typeText = typeField.getText().trim();
        if (!isValidNumericInput(typeText)) {
            showAlert("Type doit être un chiffre!");
            return;
        }
        int type = Integer.parseInt(typeText);

        Date date = java.sql.Date.valueOf(dateField.getValue());

        String prixText = prixField.getText().trim();
        if (!isValidNumericInput(prixText)) {
            showAlert("Prix doit être positif!");
            return;
        }
        double prix = Double.parseDouble(prixText);

        String description = descriptionField.getText().trim();

        if (description.isEmpty()) {
            showAlert("Veuillez remplir tous les champs !");
            return;
        }

        Transport newTransport = new Transport();
        newTransport.setTypeTransport(type);
        newTransport.setDateTransport(date);
        newTransport.setPrixTransport(prix);
        newTransport.setDescription(description);
        newTransport.setImageTransport(imagePathField.getText());

        try {
            transportService.create(newTransport);
            showAlert("Transport added successfully!");

            Stage stage = (Stage) addTransportBtn.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            showAlert("Failed to add transport: " + e.getMessage());
        }
    }



    private boolean isValidNumericInput(String input) {
        return input.matches("\\d+");
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
